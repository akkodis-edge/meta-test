DESCRIPTION = "Service image options configuration file"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
	file://service.conf \
"

inherit deploy

do_install() {
	install -d ${D}/boot
	install -m 0644 ${WORKDIR}/service.conf ${D}/boot/
}

do_deploy() {
	install -d ${DEPLOYDIR}
	install -m 0644 ${WORKDIR}/service.conf ${DEPLOYDIR}/service-${PV}.conf
	ln -s service-${PV}.conf ${DEPLOYDIR}/service.conf
}
addtask deploy after do_install before do_build

FILES:${PN} = "/boot/"
