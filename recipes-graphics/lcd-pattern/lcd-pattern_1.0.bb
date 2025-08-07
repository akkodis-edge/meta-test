DESCRIPTION = "Simple wrapper for gstreamer to output test patterns"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://lcd-pattern.sh"
S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

RDEPENDS:${PN} += "bash gstreamer1.0 gstreamer1.0-plugins-good-videobox gstreamer1.0-plugins-base-videotestsrc \
					gstreamer1.0-plugins-base-videoconvertscale gstreamer1.0-plugins-bad-waylandsink"

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${S}/lcd-pattern.sh ${D}${bindir}/lcd-pattern
}
