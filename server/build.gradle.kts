import java.util.*

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt") //<-- this one //for Hilt
    id("com.google.dagger.hilt.android") // <-- this one //for Hilt

    //
    id("kotlinx-serialization")

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
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    }
}

dependencies {
    implementation(project(":aidl-library"))
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.activity:activity-ktx:1.10.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-android:2.8.7")

    //implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")

    implementation("androidx.navigation:navigation-fragment-ktx:2.8.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.7")

    //Dagger for dependency injection
    implementation("com.google.dagger:hilt-android:2.51")
    kapt("com.google.dagger:hilt-compiler:2.51")

    //Glide for image loading
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //Retrofit for networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //Kotlin serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    //ViewModel scope
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
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