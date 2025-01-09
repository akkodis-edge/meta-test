DESCRIPTION = "Data Respons loopback interface tests"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRCREV ?= "80ae8e23cc09888c974dd6e38a15b4dee9a561ce"
SRC_URI = "gitsm://git@github.com/data-respons-solutions/loopback-test.git;protocol=ssh;branch=${BRANCH}"
BRANCH ?= "master"

S = "${WORKDIR}/git"
DEPENDS = "bluez5"

do_install () {
	install -d ${D}${prefix}/bin
	oe_runmake PREFIX=${prefix} DESTDIR=${D} install
}
