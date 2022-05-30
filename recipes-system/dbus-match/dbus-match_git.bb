DESCRIPTION = "Tool for catching dbus-signals with a timeout. \
				If you don't need the timeout, prefer dbus-monitor"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRCREV ?= "d7af4f6b0c861d4495c5d9632e28aa4ce99ab2a7"
SRC_URI = "git://git@github.com/data-respons-solutions/dbus-match.git;protocol=ssh;branch=${BRANCH}"
BRANCH ?= "master"

PV = "git${SRCPV}"
S = "${WORKDIR}/git"
RDEPENDS:${PN} = "systemd"
DEPENDS = "systemd"

do_install () {
	install -d ${D}${prefix}/bin
	oe_runmake PREFIX=${prefix} DESTDIR=${D} install
}
