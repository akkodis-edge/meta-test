DESCRIPTION = "Akkodis Edge test configs"
LICENSE = "CLOSED"

SRCREV ?= "d0a9fa51350797dc053aa3186a522fc64cf0737d"
SRC_URI = "git://git@github.com/akkodis-edge/test-cm.git;protocol=ssh;branch=main"

inherit systemd

EXTRA_OECONF = " \
	BUILD=${WORKDIR}/build \
	DESTDIR=${D} \
	datadir=${datadir} \
"

PACKAGECONFIG = " \
	configs \
	${@bb.utils.contains('MACHINE_FEATURES', 'acpi', 'ssdt', '',d)} \
"

PACKAGECONFIG[configs] = "BUILD_CONFIGS=1,BUILD_CONFIGS=0"
PACKAGECONFIG[ssdt] = "BUILD_SSDT=1,BUILD_SSDT=0,acpica-native"

do_compile() {
	oe_runmake ${EXTRA_OECONF} ${PACKAGECONFIG_CONFARGS}
}

do_install() {
	oe_runmake ${EXTRA_OECONF} ${PACKAGECONFIG_CONFARGS} install
}

FILES:${PN} = "${datadir}/test-utils/"
