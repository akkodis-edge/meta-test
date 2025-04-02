DESCRIPTION = "Service options pendrive launcher"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit systemd

SRC_URI = "file://service-options-pendrive.service.in"

RDEPENDS:${PN} += "service-options mount-pendrive"

do_install() {
	install -d ${D}${systemd_unitdir}/system
	sed -e 's:@bindir@:${bindir}:g' ${WORKDIR}/service-options-pendrive.service.in > ${WORKDIR}/service-options-pendrive.service
	install -m 0644 ${WORKDIR}/service-options-pendrive.service ${D}${systemd_unitdir}/system/
}

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "service-options-pendrive.service"
