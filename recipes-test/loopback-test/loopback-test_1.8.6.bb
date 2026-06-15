DESCRIPTION = "Akkodis Edge loopback interface tests"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRCREV ?= "d4542d5260ac733aae401e0c3214a2a6d46f5eee"
SRC_URI = "gitsm://git@github.com/akkodis-edge/loopback-test.git;protocol=ssh;branch=${BRANCH}"
BRANCH ?= "master"

# When building without static libs the --disable-static flag is passed to EXTRA_OECONF.
# Flag is not supported, disable here.
DISABLE_STATIC = ""

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
