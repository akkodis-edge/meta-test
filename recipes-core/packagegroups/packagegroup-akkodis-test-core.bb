SUMMARY = "Akkodis Edge test tools collection"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PACKAGES = "${PN}"

RDEPENDS:${PN} = " \
    grasshopper \
    grasshopper-ghcli \
    loopback-test \
    test-utils \
    test-cm \
    stressapptest \
    stress-ng \
    ssd-test \
"
