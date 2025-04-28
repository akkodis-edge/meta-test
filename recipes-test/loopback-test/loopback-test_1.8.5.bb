DESCRIPTION = "Akkodis Edge loopback interface tests"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRCREV ?= "e2fd8f5c90f8329e9335db30bde6216ae4bb7c0e"
SRC_URI = "gitsm://git@github.com/akkodis-edge/loopback-test.git;protocol=ssh;branch=${BRANCH}"
BRANCH ?= "master"

S = "${WORKDIR}/git"

EXTRA_OECONF = " \
	BUILD=${WORKDIR}/build \
	DESTDIR=${D} \
	bindir=${bindir} \
	USE_CLANG_TIDY=0 \
"

PACKAGECONFIG = " \
	${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth', 'bluetooth', '',d)} \
"

PACKAGECONFIG[bluetooth] = "USE_BLUETOOTH=1,USE_BLUETOOTH=0,bluez5"
PACKAGECONFIG[sanitizer] = "USE_SANITIZER=1,USE_SANITIZER=0,gcc-sanitizers"

do_compile() {
	oe_runmake ${EXTRA_OECONF} ${PACKAGECONFIG_CONFARGS}
}

do_install() {
	oe_runmake ${EXTRA_OECONF} ${PACKAGECONFIG_CONFARGS} install
}
