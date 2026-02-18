#!/bin/bash

RET=0

die() {
	echo "$1"
	exit 1
}

error() {
	echo "$1"
	RET=1
}

source "$1" || die "Failed loading config"
basedir="$(dirname ${1})" || die "Failed getting ${1} dirname"

for var in ${!PPP_HOST_*}; do
	name="${var#PPP_HOST_}"
	echo "Enabling PPP_HOST service \"host_$name\""
	if echo "${!var}" > "/etc/ppp/peers/host_${name}"; then
		systemctl start "ppp-host@host_${name}" || error "Failed starting PPP_HOST service"
	else
		error "Failed creating ppp-host profile"
	fi
done

for var in ${!PPP_CLIENT_*}; do
	name="${var#PPP_CLIENT_}"
	echo "Enabling PPP_CLIENT service \"client_$name\""
	if echo "${!var}" > "/etc/ppp/peers/client_${name}"; then
		systemctl start "ppp-client@client_${name}" || error "Failed starting PPP_CLIENT service"
	else
		error "Failed creating ppp-client profile"
	fi
done

for var in ${!SERIAL_ECHO_*}; do
	name="${var#SERIAL_ECHO_}"
	echo "Enabling SERIAL_ECHO service \"$name\""
	if echo "SERIAL_ECHO_OPTIONS=\"${!var}\"" > "/etc/test-utils/serial-echo/${name}.profile"; then
		systemctl start "serial-echo@${name}" || error "Failed starting SERIAL_ECHO service"
	else
		error "Failed creating serial-echo profile"
	fi
done

for var in ${!IP_ECHO_*}; do
	name="${var#IP_ECHO_}"
	echo "Enabling IP_ECHO service \"$name\""
	if echo "IP_ECHO_OPTIONS=\"${!var}\"" > "/etc/test-utils/ip-echo/${name}.profile"; then
		systemctl start "ip-echo@${name}" || error "Failed starting IP_ECHO service"
	else
		error "Failed creating ip-echo profile"
	fi
done

if [ "x$IMAGE_INSTALL" != "x" ]; then
	echo "Image installation requested"
	if [ -f /etc/default/image-install ]; then
		imagepath="$(realpath "${basedir}/${IMAGE_INSTALL}")"
		if [ -f "$imagepath" ]; then
			systemctl start image-install@"$(systemd-escape -p "$imagepath")" || error "Failed starting image installer"
		else
			error "Referenced image not found: $imagepath"
		fi
	else
		error "Image installation configuration file not available"
	fi
fi

exit "$RET"
