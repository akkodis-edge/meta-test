DESCRIPTION = "Data Respons test configs"
LICENSE = "CLOSED"

SRCREV ?= "8d84fc0e515e299a15f6461d455c18f37822b7ee"
SRC_URI = "git://git@github.com/data-respons-solutions/test-cm.git;protocol=ssh;branch=main"

inherit systemd

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "BUILD=${WORKDIR}/build DATADIR=${datadir} DESTDIR=${D}"

do_install () {
	oe_runmake install
}

FILES:${PN} = "${datadir}/test-utils/"
