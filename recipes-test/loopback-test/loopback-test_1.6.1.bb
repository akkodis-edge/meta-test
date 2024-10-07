DESCRIPTION = "Data Respons loopback interface tests"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRCREV ?= "6623d66f06f47624d95da984ddfef17848ac445a"
SRC_URI = "gitsm://git@github.com/data-respons-solutions/loopback-test.git;protocol=ssh;branch=${BRANCH}"
BRANCH ?= "master"

S = "${WORKDIR}/git"
DEPENDS = "libusb1 bluez5"

CXXFLAGS += "-Wno-stringop-truncation"

do_install () {
	install -d ${D}${prefix}/bin
	oe_runmake PREFIX=${prefix} DESTDIR=${D} install
}
