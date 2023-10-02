DESCRIPTION = "Data Respons test utilities"
LICENSE = "CLOSED"

SRCREV ?= "6a7795030399861a74fd9677d4d962e1293fb6b0"
SRC_URI = "git://git@github.com/data-respons-solutions/test-utils.git;protocol=ssh;branch=${BRANCH}"
BRANCH ?= "main"

DEPENDS = "libiio"
RDEPENDS:${PN} = "bash bc"
# coreutils for "date" and "timeout"
RDEPENDS:${PN}:append = " coreutils"
# python3 for dir-checksum
RDEPENDS:${PN}:append = " python3-crypt" 
# test-gpio for libgpiod-tools
RDEPENDS:${PN}:append = " libgpiod-tools"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "BUILD=${WORKDIR}/build"

do_install () {
	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/build/memsize ${D}${bindir}/
	install -m 0755 ${WORKDIR}/build/memalloc ${D}${bindir}/
	install -m 0755 ${WORKDIR}/build/iio-read ${D}${bindir}/
	install -m 0755 ${S}/validate-nvram.sh ${D}${bindir}/validate-nvram
	install -m 0755 ${S}/retry-until.sh ${D}${bindir}/retry-until
	install -m 0755 ${WORKDIR}/build/verify-pattern ${D}${bindir}/
	install -m 0755 ${S}/dir-checksum.py ${D}${bindir}/dir-checksum
	install -m 0755 ${S}/test-gpio.sh ${D}${bindir}/test-gpio
}
