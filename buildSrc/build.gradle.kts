plugins {
    `kotlin-dsl`
}

/*group = "hn.single.buildsrc"
version = "unspecified"*/

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(8)
}

tasks {
    val cleanBuildSrc by register("cleanBuildSrc", Delete::class) {
        delete(rootProject.file("buildSrc/build"))
    }
}
