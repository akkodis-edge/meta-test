DESCRIPTION = "Data Respons test utilities"
LICENSE = "CLOSED"

SRCREV ?= "1248e3297d7741bb3ead475285d2116fd14bea24"
SRC_URI = "git://git@github.com/data-respons-solutions/test-utils.git;protocol=ssh;branch=${BRANCH}"
BRANCH ?= "main"

DEPENDS = "libiio"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "BUILD=${WORKDIR}/build"

do_install () {
	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/build/memsize ${D}${bindir}/
	install -m 0755 ${WORKDIR}/build/memalloc ${D}${bindir}/
	install -m 0755 ${WORKDIR}/build/iio-read ${D}${bindir}/
}
