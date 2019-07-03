DESCRIPTION = "Data Respons production testing framework"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit python3-dir

SRCREV ?= "80b82eb67bf256f23f9171b4ec27f8565ca10f33"
SRC_URI = "git://git@bitbucket.datarespons.com:7999/ms/test-framework.git;protocol=ssh;branch=${BRANCH}"
BRANCH ?= "master"

PV = "git${SRCPV}"
S = "${WORKDIR}/git"
RDEPENDS_${PN} += "python3 python3-core loopback-test stress ibmtpm20tss"

EXTRA_OEMAKE += "'DESTDIR=${D}' 'PREFIX=${exec_prefix}'"
EXTRA_OEMAKE += "'PYTHON_VERSION=${PYTHON_BASEVERSION}'"

do_compile() {
}

do_install () {
	oe_runmake install
}

FILES_${PN} += "${PYTHON_SITEPACKAGES_DIR}/test_framework/*.py \
				${PYTHON_SITEPACKAGES_DIR}/test_framework/test/*.py \
				${PYTHON_SITEPACKAGES_DIR}/test_framework/provider/*.py \
				${bindir}/test-framework-exec \
				"
