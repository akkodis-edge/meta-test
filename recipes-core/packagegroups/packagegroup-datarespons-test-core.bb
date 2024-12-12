SUMMARY = "Data Respons test tools collection"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PACKAGES = "${PN}"

RDEPENDS:${PN} = " \
    grasshopper \
    grasshopper-ghcli \
    loopback-test \
    test-utils \
    stressapptest \
    stress-ng \
    ssd-test \
"
