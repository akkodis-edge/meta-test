DESCRIPTION = "Data Respons test configs"
LICENSE = "CLOSED"

SRCREV ?= "e9ef5c6cfeaab63753f7d2f7151fa85f3725a2b6"
SRC_URI = "git://git@github.com/data-respons-solutions/test-cm.git;protocol=ssh;branch=main"

inherit systemd

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "BUILD=${WORKDIR}/build DATADIR=${datadir} DESTDIR=${D}"

do_install () {
	oe_runmake install
}

FILES:${PN} = "${datadir}/test-utils/"
