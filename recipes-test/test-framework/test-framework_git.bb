DESCRIPTION = "Data Respons production testing framework"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit python3-dir python3native

SRCREV ?= "9cf300e96f8da617a9e266e56bd4c23ea5fda079"
SRC_URI = "git://git@bitbucket.datarespons.com:7999/ms/test-framework.git;protocol=ssh;branch=${BRANCH}"
BRANCH ?= "master"

PV = "git${SRCPV}"
S = "${WORKDIR}/git"

DEPENDS += "protobuf-native grpc-native"
RDEPENDS_${PN} += "python3 python3-core python3-grpcio python3-protobuf loopback-test stress ibmtpm20tss"

EXTRA_OEMAKE += " \
	'DESTDIR=${D}' \
	'PREFIX=${exec_prefix}' \
	'PYTHON_VERSION=${PYTHON_BASEVERSION}' \
"

do_install () {
	oe_runmake install
}

FILES_${PN} += " \
	${PYTHON_SITEPACKAGES_DIR}/test_framework/*.py \
	${PYTHON_SITEPACKAGES_DIR}/test_framework/tests/*.py \
	${PYTHON_SITEPACKAGES_DIR}/test_framework/providers/*.py \
	${PYTHON_SITEPACKAGES_DIR}/test_framework/proto/*.py \
	${bindir}/test-framework-exec \
"
