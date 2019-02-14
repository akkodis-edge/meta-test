DESCRIPTION = "Data Respons production testing framework"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit python3-dir

SRCREV ?= "11691c95e4bccf48215fb068ee5ea5619f53bc42"
SRC_URI = "git://git@bitbucket.datarespons.com:7999/ms/test-framework.git;protocol=ssh;branch=${BRANCH}"
BRANCH ?= "master"

PV = "git${SRCPV}"
S = "${WORKDIR}/git"
RDEPENDS_${PN} += "python3 python3-core loopback-test"
INSTALL_DIR = "${PYTHON_SITEPACKAGES_DIR}/test_framework"

do_install () {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}
    install -d ${D}${INSTALL_DIR}
    install -m 0755 ${S}/test_base.py ${D}${INSTALL_DIR}/test_base.py
    install -m 0755 ${S}/test_case.py ${D}${INSTALL_DIR}/test_case.py
    install -m 0755 ${S}/test_event.py ${D}${INSTALL_DIR}/test_event.py
    install -m 0755 ${S}/test_provider.py ${D}${INSTALL_DIR}/test_provider.py
}

FILES_${PN} = "${INSTALL_DIR}/*.py /home/root/my_test.py"
