SUMMARY = "Data Respons test tools collection"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PACKAGES = "${PN}"

RDEPENDS:${PN} = " \
	lcd-pattern \
	glmark2 \
	${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'weston-examples', '', d)} \
"
