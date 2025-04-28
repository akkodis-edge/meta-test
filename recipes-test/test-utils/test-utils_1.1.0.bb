DESCRIPTION = "Akkodis Edge test utilities"
LICENSE = "CLOSED"

SRCREV ?= "8da7f93572d0e0e576d316822fe831a79264ee82"
SRC_URI = "gitsm://git@github.com/akkodis-edge/test-utils.git;protocol=ssh;branch=${BRANCH}"
BRANCH ?= "main"

inherit systemd

S = "${WORKDIR}/git"

RDEPENDS:${PN} = "bash bc coreutils"

EXTRA_OECONF = " \
	BUILD=${WORKDIR}/build \
	DESTDIR=${D} \
	bindir=${bindir} \
	sysconfdir=${sysconfdir} \
	systemd_system_unitdir=${systemd_system_unitdir} \
	USE_CLANG_TIDY=0 \
"

PACKAGECONFIG = " \
	${@'systemd' if d.getVar('INIT_MANAGER') == 'systemd' else ''} \
	io \
	${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth', 'bluetooth', '',d)} \
	${@bb.utils.contains('DISTRO_FEATURES', 'alsa', 'audio', '',d)} \
	${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'display', '',d)} \
	gps \
"

AUDIO_RDEPENDS = " \
	gstreamer1.0 pulseaudio-server gstreamer1.0-plugins-base-audiotestsrc \
	gstreamer1.0-plugins-good-pulseaudio gstreamer1.0-plugins-good-wavparse \
	alsa-utils-speakertest \
"

PACKAGECONFIG[systemd] = "USE_SYSTEMD=1,USE_SYSTEMD=0"
PACKAGECONFIG[io] = "USE_IO=1,USE_IO=0,libiio libgpiod"
PACKAGECONFIG[bluetooth] = "USE_BLUETOOTH=1,USE_BLUETOOTH=0,sdbus-c++ sdbus-c++-tools-native,bluez5"
PACKAGECONFIG[audio] = "USE_AUDIO=1,USE_AUDIO=0,,${AUDIO_RDEPENDS}"
PACKAGECONFIG[display] = "USE_DISPLAY=1,USE_DISPLAY=0,libinput"
PACKAGECONFIG[gps] = "USE_GPS=1,USE_GPS=0,gpsd,libgps"
PACKAGECONFIG[sanitizer] = "USE_SANITIZER=1,USE_SANITIZER=0,gcc-sanitizers"

do_compile() {
	oe_runmake ${EXTRA_OECONF} ${PACKAGECONFIG_CONFARGS}
}

do_install() {
	oe_runmake ${EXTRA_OECONF} ${PACKAGECONFIG_CONFARGS} install
}

# Generated sdbus-c++ headers are placed in build-dir and the header path is included in CXXFLAGS.
# Header paths are in turn included in .debug files.
# Disable buildpaths warning.
WARN_QA:remove = "buildpaths"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = " \
	serial-echo@.service \
	ip-echo@.service \
	${@bb.utils.contains('PACKAGECONFIG', 'bluetooth', 'bt-agent.service bt-spp-echo.service', '',d)} \
"
SYSTEMD_AUTO_ENABLE = "disable"
