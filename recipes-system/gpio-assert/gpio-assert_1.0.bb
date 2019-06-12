DESCRIPTION = "Service for setting GPIO at boot"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI += " \
    file://gpio-assert@.service \
"
            
S = "${WORKDIR}"

do_install() {
	install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${S}/gpio-assert@.service ${D}${systemd_unitdir}/system/gpio-assert@.service    
}

FILES_${PN} = "${systemd_unitdir}/system/gpio-assert@.service"
