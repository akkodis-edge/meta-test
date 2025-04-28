DESCRIPTION = "Grasshopper test backend"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Proprietary;md5=0557f9d92cf58f2ccdd50f62f8ac0b28"

inherit systemd python3-dir python3native cmake

SRCREV ?= "1478618aff8d0a17fcef30a3854672d8c44b87da"
SRC_URI = "gitsm://git@github.com/akkodis-edge/grasshopper.git;protocol=ssh;branch=${BRANCH}"
BRANCH ?= "main"

S = "${WORKDIR}/git"

# grasshopper requires sqlite3_unlock_notify() api which is not provided by upstream recipe.
# See this layers recipes-support/sqlite/sqlite3_%.bbappend for a fix.
DEPENDS += "protobuf protobuf-native spdlog grpc grpc-native sqlite3 bison-native flex-native"

# cmake will find packages from targets ${RECIPE_SYSROOT}, native paths are explicitly passed in.
GRPC_CPP_PLUGIN ??= "${RECIPE_SYSROOT_NATIVE}/usr/bin/grpc_cpp_plugin"
GRPC_PYTHON_PLUGIN ??= "${RECIPE_SYSROOT_NATIVE}/usr/bin/grpc_python_plugin"
PROTOBUF_PROTOC ??= "${RECIPE_SYSROOT_NATIVE}/usr/bin/protoc"

EXTRA_OECMAKE += " \
	'-DGH_EXTERNAL_SQLITE3=ON' \
	'-DCMAKE_INSTALL_PYTHON_LIBDIR=${PYTHON_SITEPACKAGES_DIR}' \
	'-DBUILD_TESTING=OFF' \
	'-DCMAKE_GRPC_CPP_PLUGIN=${GRPC_CPP_PLUGIN}' \
	'-DCMAKE_GRPC_PYTHON_PLUGIN=${GRPC_PYTHON_PLUGIN}' \
	'-DProtobuf_PROTOC_EXECUTABLE=${PROTOBUF_PROTOC}' \
	'-DGH_USE_SANITIZER=OFF' \
"

do_install:append() {
	install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${S}/data/ghd.service.in ${D}${systemd_unitdir}/system/ghd.service
	install -d ${D}${sysconfdir}/default
	install -m 0644 ${S}/data/ghd ${D}${sysconfdir}/default/
}

# Generated flex/bison source files may contain references to TMPDIR.
WARN_QA:remove = "buildpaths"

PACKAGES += "${PN}-ghcli"

FILES:{PN} += "${systemd_unitdir}/system/ghd.service ${sysconfdir}/default/ghd"
FILES:${PN}-ghcli = "${bindir}/ghcli ${bindir}/ghrun ${bindir}/ghcondition \
					${PYTHON_SITEPACKAGES_DIR}/* ${datadir}/grasshopper/*"
RDEPENDS:${PN}-ghcli = "python3 python3-core python3-grpcio python3-protobuf python3-pyyaml"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "ghd.service"
