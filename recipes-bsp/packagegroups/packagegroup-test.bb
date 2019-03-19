SUMMARY = "Packages for Data Respons"
LICENSE = "MIT"
	
inherit packagegroup

PROVIDES = "${PACKAGES}"
PACKAGES = "\
	packagegroup-datarespons-test \
"
RDEPENDS_packagegroup-datarespons-test = "\
	test-framework \
	loopback-test \
	ssd-test \
	dbus-match \
"