#!/bin/bash

die() {
	echo "$1"
	exit 1
}

print_usage() {
    echo "Usage: ${1} [OPTIONS] PATTERN"
    echo
    echo "Display pattern until aborted by ctrl + c. "
    echo
    echo "Arguments:"
    echo " PATTERN       Pattern to display"
    echo " -w/--width    Width of output display"
    echo " -h/--height   Height of output display"
    echo " -l/--list     List available patterns"
    echo " --help:       This help message"
}

declare -A patterns
patterns[smpte]="smpte"
patterns[smpte75]="smpte75"
patterns[smpte100]="smpte100"
patterns[smpte-rp-219]="smpte-rp-219"
patterns[red]="red"
patterns[blue]="blue"
patterns[green]="green"
patterns[black]="black"
patterns[white]="white"
patterns[grey]="solid-color foreground-color=0x808080"
patterns[snow]="snow"
patterns[pinwheel]="pinwheel"
patterns[colors]="colors"
patterns[checkers-1]="checkers-1"
patterns[checkers-2]="checkers-2"
patterns[checkers-4]="checkers-4"
patterns[checkers-8]="checkers-8"
patterns[gamut]="gamut"
patterns[circular]="circular"
patterns[ball]="ball"
patterns[bar]="bar"
patterns[spokes]="spokes"
patterns[gradient]="gradient"

width=""
height=""
pattern=""

while [ $# -gt 0 ]; do
	case $1 in
	-w|--width)
		[ $# -gt 1 ] || die "Invalid argument -w/--width"
		width="$2"
		shift # past argument
		shift # past value
		;;
	-h|--height)
		[ $# -gt 1 ] || die "Invalid argument -h/--height"
		height="$2"
		shift # past argument
		shift # past value
		;;
	--help)
		print_usage
		exit 1
		;;
	-l|--list)
		for key in ${!patterns[@]}; do
			echo "$key"
		done
		exit 0
		;;
	*)
		pattern="$1"
		shift # past argument
		;;	
  esac
done

[ "x$width" != "x" ] || die "Missing mandatory argument -w/--width"
[ "x$height" != "x" ] || die "Missing mandatory argument -h/--height"
[ "x$pattern" != "x" ] || die "Missing mandatory argument PATTERN"
[ "${patterns[$pattern]+abc}" ] || die "Invalid PATTERN"

set -x
gst-launch-1.0 videotestsrc pattern=${patterns[$pattern]} ! videobox autocrop=true ! "video/x-raw, width=${width}, height=${height}" ! videoconvert ! waylandsink fullscreen=true
