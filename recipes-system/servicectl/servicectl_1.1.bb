DESCRIPTION = "Service tool"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://servicectl.py"

RDEPENDS:${PN} = "python3-core"

S = "${WORKDIR}"

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/servicectl.py ${D}${bindir}/servicectl
}

