DESCRIPTION = "Akkodis Edge test configs"
LICENSE = "CLOSED"

SRCREV ?= "75fdb55c9b50c99248da63072c8bfd19e863be41"
SRC_URI = "git://git@github.com/akkodis-edge/test-cm.git;protocol=ssh;branch=main"

inherit systemd

EXTRA_OEMAKE = "BUILD=${WORKDIR}/build DATADIR=${datadir} DESTDIR=${D}"

do_install () {
	oe_runmake install
}

FILES:${PN} = "${datadir}/test-utils/"
