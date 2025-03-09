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
    //jvmToolchain(8)
}

tasks.register("cleanSrc", Delete::class) {
    delete(rootProject.layout.buildDirectory)
    //delete(rootProject.file("buildSrc/build"))
}
