plugins {
    kotlin("jvm") version "1.9.21"
    id("com.github.davidmc24.gradle.plugin.avro") version "1.9.1"

}

group = "com.eventloopsoftware"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("org.apache.avro:avro:1.11.3")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

avro {
    setCreateSetters(true)
}