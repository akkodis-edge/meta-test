#
# The --cpp_implementation flag together with the patch allows protobuf to compile
# but it doesn't work on target. Removing the flag and patch allows both package build
# and target runtime. 
#
SRC_URI_remove += "file://0001-Add-Python-3.7-compatibility-4862.patch"
DISTUTILS_BUILD_ARGS_remove += "--cpp_implementation"
DISTUTILS_INSTALL_ARGS_remove += "--cpp_implementation"
