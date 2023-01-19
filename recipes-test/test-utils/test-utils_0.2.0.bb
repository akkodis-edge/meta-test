DESCRIPTION = "Data Respons test utilities"
LICENSE = "CLOSED"

SRCREV ?= "6a8786dc0aeebb23fd10324d1a0a1544a11e02ba"
SRC_URI = "git://git@github.com/data-respons-solutions/test-utils.git;protocol=ssh;branch=${BRANCH}"
BRANCH ?= "main"

DEPENDS = "libiio"
RDEPENDS:${PN} = "bash"
S = "${WORKDIR}/git"

EXTRA_OEMAKE = "BUILD=${WORKDIR}/build"

do_install () {
	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/build/memsize ${D}${bindir}/
	install -m 0755 ${WORKDIR}/build/memalloc ${D}${bindir}/
	install -m 0755 ${WORKDIR}/build/iio-read ${D}${bindir}/
	install -m 0755 ${S}/validate-nvram.sh ${D}${bindir}/validate-nvram
}
