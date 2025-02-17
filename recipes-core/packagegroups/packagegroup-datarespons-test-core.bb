SUMMARY = "Data Respons test tools collection"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PACKAGES = "${PN}"

RDEPENDS:${PN} = " \
    grasshopper \
    grasshopper-ghcli \
    loopback-test \
    test-utils \
    ${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth', 'test-utils-bluetooth', '', d)} \
    stressapptest \
    stress-ng \
    ssd-test \
"
