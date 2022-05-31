DESCRIPTION = "Grasshopper test backend"
LICENSE = "CLOSED"

inherit python3-dir python3native cmake

SRCREV ?= "a11fdc7cf0cc05650e8e40decd8e0cfe0500eea5"
SRC_URI = "git://git@github.com/data-respons-solutions/grasshopper.git;protocol=ssh;branch=${BRANCH}"
BRANCH ?= "main"

S = "${WORKDIR}/git"

DEPENDS += "protobuf protobuf-native spdlog grpc grpc-native"

# These paths should be detected by cmake but the protobuf package in meta-oe 
# doesn't install any cmake files which makes it difficult.
# For now the hardcoded paths are provided.
GRPC_CPP_PLUGIN ??= "${RECIPE_SYSROOT_NATIVE}/usr/bin/grpc_cpp_plugin"
GRPC_PYTHON_PLUGIN ??= "${RECIPE_SYSROOT_NATIVE}/usr/bin/grpc_python_plugin"

EXTRA_OECMAKE += " \
	'-DCMAKE_INSTALL_PYTHON_LIBDIR=${PYTHON_SITEPACKAGES_DIR}' \
	'-DBUILD_TESTING=OFF' \
	'-DCMAKE_GRPC_CPP_PLUGIN=${GRPC_CPP_PLUGIN}' \
	'-DCMAKE_GRPC_PYTHON_PLUGIN=${GRPC_PYTHON_PLUGIN}' \
"

PACKAGES += "${PN}-ghcli"

FILES:${PN}-ghcli = "${bindir}/ghcli ${PYTHON_SITEPACKAGES_DIR}/* ${datadir}/grasshopper/*"
RDEPENDS:${PN}-ghcli = "python3 python3-core python3-grpcio python3-protobuf python3-pyyaml"
