import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt") //<-- this one //for Hilt
    id("com.google.dagger.hilt.android") // <-- this one //for Hilt

    //
    id("kotlinx-serialization")

}

val gitInfo = GitInfo(rootProject.projectDir)
val applicationVersion = StringBuilder().apply {
    append(ProjectInfo.VERSION_NAME)
    val suffix = when {
        gitInfo.isRelease -> ""
        gitInfo.isReleaseCandidate -> "-${gitInfo.releaseCandidateSuffix}"
        gitInfo.isHead -> "-${gitInfo.hash}"
        else -> "-${gitInfo.branch}-${gitInfo.hash}"
    }
    append(suffix)
    append("-${ProjectInfo.BUILD_NUMBER}")
}

android {
    namespace = "hn.single.server"
    compileSdk = 35

    defaultConfig {
        applicationId = "hn.single.server"
        minSdk = 23
        //noinspection OldTargetApi
        //noinspection EditedTargetSdkVersion
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        val apiKey = getApiKey()
        buildConfigField("String", "API_KEY", apiKey)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        /*applicationVariants.all {
           val variant = this
           variant.outputs.map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
               .forEach { output ->
                   val fileName = applicationVersion.toString().replace("/", "-")
                   val outputFileName = String.format(
                       ProjectInfo.outPutFileName,
                       fileName,
                       variant.buildType.name
                   )
                   output.outputFileName = outputFileName
               }
       }*/
        setProperty(
            "archivesBaseName",
            "${ProjectInfo.NAME_SERVER}-${applicationVersion.toString().replace("/", "-")}"
        )
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        aidl = true
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(AppDependencies.AIDL_LIB))
    implementation(AppDependencies.CORE_KTX)
    implementation(AppDependencies.ANDROIDX_APP_COMPAT)
    implementation(AppDependencies.GG_MATERIAL)
    implementation(AppDependencies.CONSTRAINT_LAYOUT)
    implementation(AppDependencies.ACTIVITY_KTX)
    implementation(AppDependencies.VIEWMODEL_ANDROID)

    //implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")

    implementation(AppDependencies.FRAGMENT_KTX)
    implementation(AppDependencies.NAVIGATION_UI_KTX)

    //Dagger for dependency injection
    implementation(AppDependencies.DAGGER_HILT_ANDROID)
    kapt(AppDependencies.DAGGER_HILT_COMPILER)

    //Glide for image loading
    implementation(AppDependencies.GLIDE)

    //Retrofit for networking
    implementation(AppDependencies.RETROFIT)
    implementation(AppDependencies.CONVERTER_GSON)

    //Kotlin serialization
    implementation(AppDependencies.KOTLINX_SERIALIZATION)
    implementation(AppDependencies.SERIALIZATION_CONVERTER)
    implementation(AppDependencies.OK_HTTP)
    implementation(AppDependencies.OK_HTTP_LOGGING)

    //ViewModel scope
    implementation(AppDependencies.VIEWMODEL_KTX)

    testImplementation(AppDependencies.JUNIT)
    androidTestImplementation(AppDependencies.EXT_JUNIT)
    androidTestImplementation(AppDependencies.ESPRESSO_CORE)
}

fun getApiKey(): String {
    val properties = Properties()
    val keystoreFile = project.rootProject.file("gradle.properties")
    properties.load(keystoreFile.inputStream())
    //val apiKey = properties.getProperty("apiKey") ?: ""

    val propertiesFile = project.rootProject.file("gradle.properties")
    val apiKey = if (propertiesFile.exists()) {
        properties.load(propertiesFile.inputStream())
        properties.getProperty("apiKey") ?: ""
    } else {
        System.getenv("apiKey") ?: ""
    }
    return apiKey
}

tasks.named("preBuild") {
    dependsOn("taskCreateDefaultConfig")
}


tasks.register("convertSVGs") {
    println("------convertSVGs------")
    doLast {
        // Đảm bảo python chạy đúng thư mục
        val scriptFile = file("scripts/convert_svg.py")
        val workingDirectory = file(".")
        exec {
            // Gọi Python chuẩn
            commandLine("python", scriptFile.absolutePath)
            workingDir = workingDirectory
            // Cho phép log lỗi script nếu cần
            //isIgnoreExitValue = false
            standardOutput = System.out
            errorOutput = System.err
        }
    }
}

val createDefaultConfig: Task = tasks.create("createDefaultConfig") {
    println("------Create json data file------")
    //val resourcesDir = File("${rootDir.path}/app/src/main/assets").apply { mkdirs() }
    val resourcesDir = File("${projectDir}/src/main/assets").apply {
        if (this.exists().not()) mkdirs()
    }
    val resource = File(resourcesDir, "data_custom.json")
    val dateTime = SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())
    resource.writeText(
        "{\n" +
                "  \"version\": \"$dateTime\",\n" +
                "  \"name\": \"data\",\n" +
                "  \"isCustom\": \"true\"\n" +
                "}"
    )
}

val taskCreateDefaultConfig by tasks.registering {
    group = "build"
    description = "Build file data_custom.json in assets"
    doLast {
        println("------Create json data file------")
        val resourcesDir = File("${projectDir}/src/main/assets").apply { mkdirs() }
        val resource = File(resourcesDir, "data_data_custom.json")
        val dateTime = SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().time)
        resource.writeText(
            """
            {
              "version": "$dateTime",
              "name": "data",
              "isCustom": true
            }
            """.trimIndent()
        )
        println("------[Success] File created: ${resource.absolutePath}------")
    }
}