object ProjectInfo {

    private const val GROUP_SERVER = "hn.single"
    const val NAME_SERVER = "Server-app"
    const val NAME_LIB = "Lib-app"
    const val NAME_APP = "Client-app"
    const val SERVER_APPLICATION_ID = "$GROUP_SERVER.server"
    const val APP_APPLICATION_ID = "com.app.func"

    const val outPutFileName = "%s-%s.apk"

    const val majorVersion = 1
    const val minorVersion = 0
    const val patchVersion = 0
    const val buildNumber = 0
    const val versionName = "$majorVersion.$minorVersion.$patchVersion"
    const val versionCode = 1_000_00_00 * majorVersion + 1_00_00 * minorVersion + 1_00 * patchVersion + buildNumber
}