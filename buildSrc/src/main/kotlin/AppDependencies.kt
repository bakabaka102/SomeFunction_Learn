object AppDependencies {

    const val AIDL_LIB = ":aidl-library"
    const val CORE_KTX = "androidx.core:core-ktx:${Version.CORE_KTX}"
    const val ANDROIDX_APP_COMPAT = "androidx.appcompat:appcompat:${Version.ANDROIDX_APP_COMPAT}"
    const val GG_MATERIAL = "com.google.android.material:material:${Version.GG_MATERIAL}"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Version.CONSTRAINT_LAYOUT}"
    const val ACTIVITY_KTX = "androidx.activity:activity-ktx:${Version.ACTIVITY_KTX}"

    const val FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:${Version.FRAGMENT_KTX}"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${Version.NAVIGATION_UI_KTX}"

    const val DAGGER_HILT_ANDROID = "com.google.dagger:hilt-android:${Version.DAGGER_HILT}"
    const val DAGGER_HILT_COMPILER = "com.google.dagger:hilt-compiler:${Version.DAGGER_HILT}"

    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Version.RETROFIT}"
    const val CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:${Version.CONVERTER_GSON}"

    const val KOTLINX_SERIALIZATION = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Version.KOTLINX_SERIALIZATION}"
    const val SERIALIZATION_CONVERTER = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Version.SERIALIZATION_CONVERTER}"

    const val VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.VIEWMODEL_KTX}"
    const val VIEWMODEL_ANDROID = "androidx.lifecycle:lifecycle-viewmodel-android:${Version.VIEWMODEL_ANDROID}"

    const val JUNIT = "junit:junit:${Version.JUNIT}"
    const val EXT_JUNIT = "androidx.test.ext:junit:${Version.EXT_JUNIT}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Version.ESPRESSO_CORE}"

    const val LEGACY_SUPPORT_V4 = "androidx.legacy:legacy-support-v4:${Version.LEGACY_SUPPORT_V4}"
    const val LIFECYCLE_LIVEDATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:${Version.LIFECYCLE_LIVEDATA_KTX}"
    const val LIFECYCLE_VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.LIFECYCLE_VIEWMODEL_KTX}"

    const val GLIDE = "com.github.bumptech.glide:glide:${Version.GLIDE}"
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${Version.GLIDE_COMPILER}"

    const val RXJAVA2 = "io.reactivex.rxjava2:rxjava:${Version.RXJAVA2}"
    const val RX_ANDROID_2 = "io.reactivex.rxjava2:rxandroid:${Version.RX_ANDROID_2}"
    const val RXJAVA3 = "io.reactivex.rxjava3:rxjava:${Version.RXJAVA3}"
    const val RX_ANDROID_3 = "io.reactivex.rxjava3:rxandroid:${Version.RX_ANDROID_3}"

    const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.COROUTINES}"
    const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.COROUTINES}"
    const val RETROFIT_COROUTINES_ADAPTER = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Version.RETROFIT_COROUTINES_ADAPTER}"
    const val CONVERTER_SCALARS = "com.squareup.retrofit2:converter-scalars:${Version.CONVERTER_SCALARS}"
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Version.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Version.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Version.ROOM}"
    const val ROOM_RXJAVA3 = "androidx.room:room-rxjava3:${Version.ROOM}"

    const val OKHTTP_BOM = "com.squareup.okhttp3:okhttp-bom:${Version.OK_HTTP}"
    const val OK_HTTP = "com.squareup.okhttp3:okhttp:${Version.OK_HTTP}"
    const val OK_HTTP_LOGGING = "com.squareup.okhttp3:logging-interceptor:${Version.OK_HTTP}"
    const val JAVAX_ANNOTATION = "org.glassfish:javax.annotation:${Version.JAVAX_ANNOTATION}"
    const val COMPOSE_RUNTIME = "androidx.compose.runtime:runtime:${Version.COMPOSE}"

    const val CONVERTER_MOSHI = "com.squareup.retrofit2:converter-moshi:${Version.CONVERTER_MOSHI}"
    const val MOSHI = "com.squareup.moshi:moshi:${Version.MOSHI}"
    const val MOSHI_KOTLIN = "com.squareup.moshi:moshi-kotlin:${Version.MOSHI}"
    const val WORK_RUNTIME_KTX = "androidx.work:work-runtime-ktx:${Version.WORK_RUNTIME_KTX}"

    const val LOTTIE = "com.airbnb.android:lottie:${Version.LOTTIE_VERSION}"

    object Version {
        const val CORE_KTX = "1.15.0"
        const val ANDROIDX_APP_COMPAT = "1.7.0"
        const val GG_MATERIAL = "1.12.0"
        const val CONSTRAINT_LAYOUT = "2.2.0"
        const val ACTIVITY_KTX = "1.10.0"
        const val FRAGMENT_KTX = "2.8.7"
        const val NAVIGATION_UI_KTX = "2.8.7"
        const val DAGGER_HILT = "2.51"
        const val GLIDE = "4.16.0"
        const val RETROFIT = "2.9.0"
        const val CONVERTER_GSON = "2.9.0"
        const val KOTLINX_SERIALIZATION = "1.6.3"
        const val SERIALIZATION_CONVERTER = "1.0.0"
        const val OK_HTTP = "4.12.0"
        const val VIEWMODEL_KTX = "2.8.7"
        const val VIEWMODEL_ANDROID = "2.8.7"
        const val JUNIT = "4.13.2"
        const val EXT_JUNIT = "1.2.1"
        const val ESPRESSO_CORE = "3.6.1"

        const val LEGACY_SUPPORT_V4 = "1.0.0"
        const val LIFECYCLE_LIVEDATA_KTX = "2.8.7"
        const val LIFECYCLE_VIEWMODEL_KTX = "2.8.7"
        const val GLIDE_COMPILER = "4.13.1"
        const val RXJAVA2 = "2.2.21"
        const val RX_ANDROID_2 = "2.1.1"
        const val RXJAVA3 = "3.1.3"
        const val RX_ANDROID_3 = "3.0.0"
        const val COROUTINES = "1.9.0"
        const val RETROFIT_COROUTINES_ADAPTER = "0.9.2"
        const val CONVERTER_SCALARS = "2.9.0"
        const val CONVERTER_MOSHI = "2.9.0"
        const val MOSHI = "1.12.0"
        const val ROOM = "2.6.1"
        const val JAVAX_ANNOTATION = "10.0-b28"
        const val COMPOSE = "1.7.8"
        const val WORK_RUNTIME_KTX = "2.10.0"

        const val LOTTIE_VERSION = "6.1.0"
    }
}