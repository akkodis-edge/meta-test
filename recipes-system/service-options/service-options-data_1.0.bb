DESCRIPTION = "Service options /data launcher"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit systemd

SRC_URI = "file://service-options-data.service.in"

RDEPENDS:${PN} += "service-options mount-data"

do_install() {
	install -d ${D}${systemd_unitdir}/system
	sed -e 's:@bindir@:${bindir}:g' ${WORKDIR}/service-options-data.service.in > ${WORKDIR}/service-options-data.service
	install -m 0644 ${WORKDIR}/service-options-data.service ${D}${systemd_unitdir}/system/
}

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "service-options-data.service"
