DESCRIPTION = "Data Respons test configs"
LICENSE = "CLOSED"

SRCREV ?= "3f3a2390b7f5ae81d95fbb95f8f7aebff370c9a9"
SRC_URI = "git://git@github.com/data-respons-solutions/test-cm.git;protocol=ssh;branch=main"

inherit systemd

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "BUILD=${WORKDIR}/build DATADIR=${datadir} DESTDIR=${D}"

do_install () {
	oe_runmake install
}

FILES:${PN} = "${datadir}/test-utils/"
