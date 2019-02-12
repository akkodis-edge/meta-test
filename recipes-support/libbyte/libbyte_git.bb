DESCRIPTION = "Data Respons library for converting int(bytes) to suffixed string"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRCREV ?= "bddb20ff26ad0bc65cf66511bcf174cc4b714d5a"
SRC_URI = "git://git@bitbucket.datarespons.com:7999/ms/libbyte.git;protocol=ssh;branch=${BRANCH}"
BRANCH ?= "master"

PV = "git${SRCPV}"
S = "${WORKDIR}/git"

do_install () {
	install -d ${D}${prefix}/lib
	install -d ${D}${prefix}/include
	oe_runmake PREFIX=${prefix} DESTDIR=${D} install
}
