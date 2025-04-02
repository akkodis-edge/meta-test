DESCRIPTION = "Service options"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit systemd

SRC_URI = " \
	file://service-options@.service.in \
	file://service-options.sh \
	file://ppp-host@.service.in \
	file://image-install@.service.in \
"

RDEPENDS:${PN} += "bash ppp dos2unix sysctl-ip-forward"

do_install() {
	install -d ${D}${sbindir}
	install -m 0755 ${WORKDIR}/service-options.sh ${D}${sbindir}/service-options
	install -d ${D}${systemd_unitdir}/system
	sed -e 's:@sbindir@:${sbindir}:g' ${WORKDIR}/service-options@.service.in > ${WORKDIR}/service-options@.service
	install -m 0644 ${WORKDIR}/service-options@.service ${D}${systemd_unitdir}/system/

	sed -e 's:@sbindir@:${sbindir}:g' ${WORKDIR}/ppp-host@.service.in > ${WORKDIR}/ppp-host@.service
	install -m 0644 ${WORKDIR}/ppp-host@.service ${D}${systemd_unitdir}/system/

	sed -e 's:@sbindir@:${sbindir}:g' -e 's:@sysconfdir@:${sysconfdir}:g' \
		${WORKDIR}/image-install@.service.in > ${WORKDIR}/image-install@.service
	install -m 0644 ${WORKDIR}/image-install@.service ${D}${systemd_unitdir}/system/
}

# The IMAGE_INSTALL option relies on configuration file /etc/default/image-install
# which provides arguments in variable "OPTIONS" for install-image-container utility.
# An optional feature is the possibility to signal installation state.
# Provide an executable utility ${sbindir}/image-install-status which accepts a single argument.
# Argument may be:
#   "start": installation started.
#   "success": installation successful.
#   *: All other values mena installation failed.
RRECOMMENDS:${PN} = "image-install-conf"

SYSTEMD_PACKAGES = "${PN}"
FILES:${PN} += "${systemd_system_unitdir}/ppp-host@.service ${systemd_system_unitdir}/service-options@.service ${systemd_system_unitdir}/image-install@.service"
