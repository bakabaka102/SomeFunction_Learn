plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    compileSdk = 35

    defaultConfig {
        applicationId = "com.app.func"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
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
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
        compose = true
        aidl = true
        buildConfig = true
    }

    namespace = "com.app.func"
}

dependencies {
    implementation(AppDependencies.COMPOSE_RUNTIME)
    implementation(AppDependencies.CORE_KTX)
    implementation(AppDependencies.ANDROIDX_APP_COMPAT)
    implementation(AppDependencies.GG_MATERIAL)
    implementation(AppDependencies.CONSTRAINT_LAYOUT)
    implementation(AppDependencies.FRAGMENT_KTX)
    implementation(AppDependencies.NAVIGATION_UI_KTX)
    implementation(AppDependencies.LEGACY_SUPPORT_V4)
    implementation(AppDependencies.LIFECYCLE_LIVEDATA_KTX)
    implementation(AppDependencies.LIFECYCLE_VIEWMODEL_KTX)

    testImplementation(AppDependencies.JUNIT)
    androidTestImplementation(AppDependencies.EXT_JUNIT)
    androidTestImplementation(AppDependencies.ESPRESSO_CORE)

    implementation(AppDependencies.GLIDE)
    annotationProcessor(AppDependencies.GLIDE_COMPILER)
    implementation(AppDependencies.RXJAVA2)
    implementation(AppDependencies.RX_ANDROID_2)
    implementation(AppDependencies.RXJAVA3)
    implementation(AppDependencies.RX_ANDROID_3)
    implementation(AppDependencies.COROUTINES_CORE)
    implementation(AppDependencies.COROUTINES_ANDROID)
    implementation(AppDependencies.RETROFIT_COROUTINES_ADAPTER)
    implementation(AppDependencies.CONVERTER_SCALARS)
    implementation(AppDependencies.ROOM_RUNTIME)
    implementation(AppDependencies.ROOM_KTX)
    ksp(AppDependencies.ROOM_COMPILER)
    implementation(AppDependencies.ROOM_RXJAVA3)
    implementation(AppDependencies.RETROFIT)
    implementation(AppDependencies.CONVERTER_GSON)
    
    //Kotlin serialization
    implementation(AppDependencies.KOTLINX_SERIALIZATION)
    implementation(AppDependencies.SERIALIZATION_CONVERTER)
    implementation(AppDependencies.OK_HTTP)

    implementation(AppDependencies.CONVERTER_MOSHI)
    implementation(AppDependencies.MOSHI)
    implementation(AppDependencies.MOSHI_KOTLIN)

    implementation(platform(AppDependencies.OKHTTP_BOM))
    implementation(AppDependencies.OKHTTP)
    implementation(AppDependencies.LOGGING_INTERCEPTOR)
    compileOnly(AppDependencies.JAVAX_ANNOTATION)

    implementation(project(AppDependencies.AIDL_LIB))

    implementation(AppDependencies.WORK_RUNTIME_KTX)

}