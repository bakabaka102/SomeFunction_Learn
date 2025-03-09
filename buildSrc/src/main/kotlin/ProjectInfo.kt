object ProjectInfo {

    const val group = "com.ingenico"
    const val name = "tms-client-config-app"
    const val applicationId = "$group.tms.client.config"

    const val sonarKey = "$group:tms.client.config"
    const val sonarName = "TMS Client config"
    const val outPutFileName = "%s-%s.apk"

    const val majorVersion = 1
    const val minorVersion = 0
    const val patchVersion = 0
    const val buildNumber = 0
    const val versionName = "$majorVersion.$minorVersion.$patchVersion"
    const val versionCode = 1_000_00_00 * majorVersion + 1_00_00 * minorVersion + 1_00 * patchVersion + buildNumber
}