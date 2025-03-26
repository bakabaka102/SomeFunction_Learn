object ProjectInfo {

    private const val GROUP_SERVER = "hn.single"
    const val NAME_SERVER = "Server-app"
    const val NAME_LIB = "Lib-app"
    const val NAME_APP = "Client-app"
    const val SERVER_APPLICATION_ID = "$GROUP_SERVER.server"
    const val APP_APPLICATION_ID = "com.app.func"

    const val OUTPUT_FILE_NAME = "%s-%s.apk"

    const val MAJOR_VERSION = 1
    const val MINOR_VERSION = 0
    const val PATCH_VERSION = 0
    const val BUILD_NUMBER = 0
    const val VERSION_NAME = "$MAJOR_VERSION.$MINOR_VERSION.$PATCH_VERSION"
    const val VERSION_CODE = 1_000_00_00 * MAJOR_VERSION + 1_00_00 * MINOR_VERSION + 1_00 * PATCH_VERSION + BUILD_NUMBER
}