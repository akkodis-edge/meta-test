DESCRIPTION = "Data Respons prng library"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRCREV ?= "7c6451b0cdfeec6ae3431bfa52b8dbad9314ccb8"
SRC_URI = "git://git@bitbucket.datarespons.com:7999/ms/libprng.git;protocol=ssh;branch=${BRANCH}"
BRANCH ?= "master"

PV = "git${SRCPV}"
S = "${WORKDIR}/git"

do_install () {
	install -d ${D}${prefix}/lib
	install -d ${D}${prefix}/include
	oe_runmake PREFIX=${prefix} DESTDIR=${D} install
}
