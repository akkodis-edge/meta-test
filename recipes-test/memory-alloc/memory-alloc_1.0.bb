DESCRIPTION = "Test allocating memory"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://memory-alloc.c"

S = "${WORKDIR}"

do_compile() {
	${CC} memory-alloc.c ${LDFLAGS} ${CFLAGS} -o memory-alloc
}

do_install() {
	 install -d ${D}${bindir}
	 install -m 0755 ${S}/memory-alloc ${D}${bindir}
}