DESCRIPTION = "Akkodis Edge ssd sudden power down test"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRCREV ?= "d4b233fe7060af99609804714f918b08f7513602"
SRC_URI = "gitsm://git@github.com/akkodis-edge/ssd-test.git;protocol=ssh;branch=${BRANCH}"
BRANCH ?= "master"

PV = "git${SRCPV}"

do_install () {
	install -d ${D}${prefix}/bin
	oe_runmake PREFIX=${prefix} DESTDIR=${D} install
}
