DESCRIPTION = "Service options"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# The IMAGE_INSTALL option relies on configuration file /etc/default/image-install
# which provides arguments in variable "OPTIONS" for install-image-container utility.
# An optional feature for installation is the possibility to signal installation state.
# Provide an executable utility ${sbindir}/image-install-status which accepts a single argument.
# Argument may be:
#   "start": installation started.
#   "success": installation successful.
#   *: All other values mean installation failed.

inherit systemd

SRC_URI = " \
	file://service-options.service.in \
	file://service-options.sh \
	file://ppp-host@.service.in \
	file://ppp-client@.service.in \
	file://10dummyroute \
	file://10removedummy \
	file://image-install@.service.in \
"
S = "${UNPACKDIR}"

RDEPENDS:${PN} += "bash ppp dos2unix sysctl-ip-forward debianutils-run-parts"

do_install() {
	install -d ${D}${sbindir}
	install -m 0755 ${S}/service-options.sh ${D}${sbindir}/service-options
	install -d ${D}${systemd_unitdir}/system
	sed -e 's:@sbindir@:${sbindir}:g' ${S}/service-options.service.in > ${S}/service-options.service
	install -m 0644 ${S}/service-options.service ${D}${systemd_unitdir}/system/

	sed -e 's:@sbindir@:${sbindir}:g' ${S}/ppp-host@.service.in > ${S}/ppp-host@.service
	install -m 0644 ${S}/ppp-host@.service ${D}${systemd_unitdir}/system/
	sed -e 's:@sbindir@:${sbindir}:g' ${S}/ppp-client@.service.in > ${S}/ppp-client@.service
	install -m 0644 ${S}/ppp-client@.service ${D}${systemd_unitdir}/system/
	install -d ${D}${sysconfdir}/ppp/ip-up.d
	install -d ${D}${sysconfdir}/ppp/ip-down.d
	install -d ${D}${sysconfdir}/ppp/peers
	install -m 0755 ${S}/10dummyroute ${D}${sysconfdir}/ppp/ip-up.d/
	install -m 0755 ${S}/10removedummy ${D}${sysconfdir}/ppp/ip-down.d/

	sed -e 's:@sbindir@:${sbindir}:g' -e 's:@sysconfdir@:${sysconfdir}:g' \
		${S}/image-install@.service.in > ${S}/image-install@.service
	install -m 0644 ${S}/image-install@.service ${D}${systemd_unitdir}/system/
}

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "service-options.service"
FILES:${PN} += "${systemd_system_unitdir}/ppp-host@.service ${systemd_system_unitdir}/service-options.service ${systemd_system_unitdir}/image-install@.service"
FILES:${PN} += "${systemd_system_unitdir}/ppp-client@.service"
