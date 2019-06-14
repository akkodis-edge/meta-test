#!/usr/bin/python3

import sys, os, argparse, subprocess, tempfile, shutil

def get_mount_point(device):
    try:
        return subprocess.run(['findmnt', device, '-no', 'TARGET'], 
                              stdout=subprocess.PIPE, check=True, universal_newlines=True).stdout
    except subprocess.CalledProcessError:
        return None


def extract_systemd_journal(root_path, out_dir):
    systemd_journal_readme = \
"""
Journal is extracted in "Journal Export Format", to operate on log through journalctl it needs
to be converted back to journal format first.

Convert export to journal:
$ cat systemd-journal-export | systemd-journal-remote -o systemd.journal

Using journal file:
$ journalctl --file=systemd.journal

"""
    
    out_file = os.path.join(out_dir, 'systemd-journal-export')
    readme = os.path.join(out_dir, 'systemd-journal-export.README')
    journal_dir = os.path.join(root_path, 'var/log/journal')
    
    if not os.path.isdir(journal_dir):
        raise NotADirectoryError(journal_dir)
    
    with open(out_file, 'w') as fp: 
        subprocess.run(['journalctl', '--dir', journal_dir, '-o', 'export'],
                           stdout=fp, check=True)
        
    with open(readme, 'w') as fp:
        fp.write(systemd_journal_readme)
    
def extract_nvram(out_dir):
    out_file = os.path.join(out_dir, 'nvram.txt')
    
    with open(out_file, 'w') as fp:
        subprocess.run(['nvram', 'list'],
                       stdout=fp, check=True)
        
def extract_paths(root_path, out_dir, file_list):
    for f in file_list:
        if f[0] == '/':
            f = f[1:]
        
        os.makedirs(os.path.dirname(os.path.join(out_dir, f)), exist_ok=True)
        
        subprocess.run(['cp', '-ar', os.path.join(root_path, f) , os.path.join(out_dir, f)], 
                       check=True)
        
def extract_oe(root_path, out_dir):
    files = ['/etc/image_info', '/etc/distro_info']
    
    extract_paths(root_path, out_dir, files)    

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Data Respons service tool', prog='servicectl')
    parser.add_argument('OUT', help='Name of output file')
    parser.add_argument('IN', nargs='*', help='Filesystem paths added as sections')
    parser.add_argument('--extract', action='store_true', help='Extract requested sections as tar.bz2')
    parser.add_argument('--systemd', action='store_true', help='Add systemd section')
    parser.add_argument('--nvram', action='store_true', help='Add nvram section')
    parser.add_argument('--oe', action='store_true', help='Add openembedded section')
    parser.add_argument('--part', help='Target partition, use current root partition if not specified')
    
    args = parser.parse_args()
    
    root_path = '/'
    unmount = False
    error = False

    try:
        if args.part:
            root_path = get_mount_point(args.part)
            if not root_path:
                mount_point = tempfile.TemporaryDirectory()
                root_path = mount_point.name
                subprocess.run(['mount' ,'-o', 'ro', args.part, root_path], check=True)
                unmount = True
        
        
        if args.extract:
            with tempfile.TemporaryDirectory() as dir:
                backup = []
                if args.systemd:
                    backup.append(['systemd_journal', extract_systemd_journal, [root_path, dir]])
                    
                if args.nvram:
                    backup.append(['nvram', extract_nvram, [dir]])
                    
                if args.oe:
                    backup.append(['openembedded', extract_oe, [root_path, dir]])
                    
                if args.IN:
                    backup.append(['filesystem_paths', extract_paths, [root_path, dir, args.IN]])
                    
                
                for name, task, arg, in backup:
                    print('{}...'.format(name), end='')
                    try:
                        task(*arg)
                        print('OK')
                    except Exception as e:
                        print('FAIL: {}'.format(e))
                        error = True
                        
        
                archive_name = args.OUT 
                if archive_name.endswith('.tar.bz2'):
                    archive_name = archive_name[:-8]
                        
                print('Creating archive {}.tar.bz2 with content: '.format(archive_name))
                for root, dirs, files in os.walk(dir):
                    for name in files:
                        print('{}'.format(os.path.join(root, name).replace(dir, '...')))
                    for name in dirs:
                        print('{}/'.format(os.path.join(root, name).replace(dir, '...')))
                
                shutil.make_archive(archive_name, format='bztar', root_dir=dir)
            
        
        if not error:
            sys.exit(0)
        
    except subprocess.CalledProcessError as e:
        print('{} [{}]: {}'.format(e.cmd, e.returncode, e.output))
    
    finally:
        if unmount:
            subprocess.call(['umount', args.part])
        
    sys.exit(1)    
        