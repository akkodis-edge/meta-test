LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://ip_forward.conf"

do_install () {
	install -d ${D}${sysconfdir}/sysctl.d/
	install -m 0644 ${WORKDIR}/ip_forward.conf ${D}${sysconfdir}/sysctl.d/
}

FILES:${PN} += "${sysconfdir}/sysctl.d/*"
