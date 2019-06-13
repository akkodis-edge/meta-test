#!/usr/bin/python3

import sys, os, argparse, subprocess, tempfile, shutil

def get_mount_point(device):
    try:
        return subprocess.run(['findmnt', device, '-no', 'TARGET'], 
                              stdout=subprocess.PIPE, check=True, universal_newlines=True).stdout
    except subprocess.CalledProcessError:
        return None


def backup_systemd_journal(root_path, out_dir):
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
    journal_dir = '/var/log/journal'
    
    if not os.path.isdir(journal_dir):
        raise NotADirectoryError(journal_dir)
    
    with open(out_file, 'w') as fp: 
        subprocess.run(['journalctl', '--dir', os.path.join(root_path, '/var/log/journal'), '-o', 'export'],
                           stdout=fp, check=True)
        
    with open(readme, 'w') as fp:
        fp.write(systemd_journal_readme)
    
def backup_nvram(out_dir):
    out_file = os.path.join(out_dir, 'nvram.txt')
    
    with open(out_file, 'w') as fp:
        subprocess.run(['nvram', 'list'],
                       stdout=fp, check=True)

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Data Respons service tool', prog='servicectl')
    parser.add_argument('--backup', help='Name for tar.bz2 archive of interesting data for debugging')
    parser.add_argument('--partition', help='Target partition, use current root partition if not specified')
    
    args = parser.parse_args()
    
    root_path = '/'
    unmount = False
    error = False
    
    try:
        if args.partition:
            root_path = get_mount_point(args.partition)
            if not root_path:
                mount_point = tempfile.TemporaryDirectory()
                root_path = mount_point.name
                subprocess.run(['mount' ,'-o', 'ro', args.partition, root_path], check=True)
                unmount = True
        
        
        if args.backup:
            with tempfile.TemporaryDirectory() as dir:
                backup = [['systemd_journal', backup_systemd_journal, [root_path, dir]],
                          ['nvram', backup_nvram, [dir]]]
                
                for name, task, arg, in backup:
                    print('{}...'.format(name), end='')
                    try:
                        task(*arg)
                        print('OK')
                    except:
                        print('FAIL')
                        error = True
        
                archive_name = args.backup
                if archive_name.endswith('.tar.bz2'):
                    archive_name = archive_name[:-8]

                shutil.make_archive(archive_name, format='bztar', root_dir=dir)
            
        
        if not error:
            sys.exit(0)
        
    except subprocess.CalledProcessError as e:
        print('{} [{}]: {}'.format(e.cmd, e.returncode, e.output))
    
    finally:
        if unmount:
            subprocess.call(['umount', args.partition])
        
    sys.exit(1)    
        