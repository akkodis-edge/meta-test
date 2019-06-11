DESCRIPTION = "Stress tester"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://MODULE_LICENSE_APACHE2;md5=d41d8cd98f00b204e9800998ecf8427e"

SRC_URI = "git://anongit.freedesktop.org/evtest"

SRC_URI = "git://github.com/stressapptest/stressapptest.git;protocol=https"


S = "${WORKDIR}/git"

inherit autotools

SRCREV ?= "fb72e5e5f0879231f38e0e826a98a6ca2d1ca38e"