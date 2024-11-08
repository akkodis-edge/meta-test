SUMMARY = "Data Respons test tools collection"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PACKAGES = "${PN} packagegroup-datarespons-test-touch"

RDEPENDS:${PN} = " \
	lcd-pattern \
	glmark2 \
	${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'weston-examples', '', d)} \
"

RDEPENDS:packagegroup-datarespons-test-touch = " \
	libinput \
	libinput-bin \
	python3-libevdev \
	python3-pyudev \
	tslib \
	tslib-tests \
	tslib-conf \
	tslib-uinput \
	tslib-calibrate \
"
