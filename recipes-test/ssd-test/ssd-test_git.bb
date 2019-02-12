DESCRIPTION = "Data Respons ssd sudden power down test"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRCREV ?= "3bd454a16cad3515e633486e4a0c65ef0a4218b4"
SRC_URI = "git://git@bitbucket.datarespons.com:7999/ms/ssd-test.git;protocol=ssh;branch=${BRANCH}"
BRANCH ?= "master"

PV = "git${SRCPV}"
S = "${WORKDIR}/git"
DEPENDS = "libbyte libprng"

do_install () {
	install -d ${D}${prefix}/bin
	oe_runmake PREFIX=${prefix} DESTDIR=${D} install
}
