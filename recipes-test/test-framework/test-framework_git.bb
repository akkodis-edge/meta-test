DESCRIPTION = "Data Respons production testing framework"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit python3-dir python3native

SRCREV ?= "83c33f9b0830e3f5da5fb05678576fd30dbe4734"
SRC_URI = "git://git@github.com/data-respons-solutions/test-framework.git;protocol=ssh;branch=${BRANCH}"
BRANCH ?= "master"

PV = "git${SRCPV}"
S = "${WORKDIR}/git"

DEPENDS += "protobuf-native grpc-native"
RDEPENDS:${PN} += "python3 python3-core python3-grpcio python3-protobuf loopback-test stress"

EXTRA_OEMAKE += " \
	'DESTDIR=${D}' \
	'PREFIX=${exec_prefix}' \
	'PYTHON_VERSION=${PYTHON_BASEVERSION}' \
"

do_install () {
	oe_runmake install
}

FILES:${PN} += " \
	${PYTHON_SITEPACKAGES_DIR}/test_framework/*.py \
	${PYTHON_SITEPACKAGES_DIR}/test_framework/tests/*.py \
	${PYTHON_SITEPACKAGES_DIR}/test_framework/providers/*.py \
	${PYTHON_SITEPACKAGES_DIR}/test_framework/proto/*.py \
	${bindir}/test-framework-exec \
"
