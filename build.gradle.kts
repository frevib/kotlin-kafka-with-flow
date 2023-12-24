plugins {
    kotlin("jvm") version "1.9.21"
    id("com.github.davidmc24.gradle.plugin.avro") version "1.9.1"
}

group = "com.eventloopsoftware"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://packages.confluent.io/maven/")
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("org.apache.avro:avro:1.11.3")
    implementation("org.apache.kafka:kafka-clients:3.6.1")
    implementation("io.confluent:kafka-json-serializer:7.5.1")
    implementation("io.confluent:kafka-avro-serializer:7.5.1")
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.6.2")
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
//    implementation("com.fasterxml.jackson.core:jackson-databind:[2.8.11.1,)")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")
    implementation("com.google.code.gson:gson:2.10.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

avro {
//    setCreateSetters(true)
//    stringType.set("CharSequence")
//    fieldVisibility.set("private")
//    customConversion(org.apache.avro.Conversions.UUIDConversion::class.java)
}

//idea {
//    version = "2023.3.2"
//}