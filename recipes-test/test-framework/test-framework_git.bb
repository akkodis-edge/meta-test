DESCRIPTION = "Data Respons production testing framework"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit python3-dir

SRCREV ?= "bda8de40322ce43f02a689122bd315f7bc258dc0"
SRC_URI = "git://git@bitbucket.datarespons.com:7999/ms/test-framework.git;protocol=ssh;branch=${BRANCH}"
BRANCH ?= "master"

PV = "git${SRCPV}"
S = "${WORKDIR}/git"
RDEPENDS_${PN} += "python3 python3-core loopback-test stress"

INSTALL_DIR = "${PYTHON_SITEPACKAGES_DIR}/test_framework"

do_install () {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}
    install -d ${D}${INSTALL_DIR}
    install -d ${D}${INSTALL_DIR}/test
    install -d ${D}${INSTALL_DIR}/provider
    install -m 0755 ${S}/*.py ${D}${INSTALL_DIR}
    install -m 0755 ${S}/test/*.py ${D}${INSTALL_DIR}/test
    install -m 0755 ${S}/provider/*.py ${D}${INSTALL_DIR}/provider
}

FILES_${PN} += "${INSTALL_DIR}/*.py ${INSTALL_DIR}/test/*.py ${INSTALL_DIR}/provider/*.py"
