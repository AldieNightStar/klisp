val MAIN_CLASS = "haxidenti.klisp.MainKt"
val PROJECT_NAME = "klisp"
val AUTHOR = "HaxiDenti"
val VERSION = "1.0.0"

plugins {
    kotlin("jvm") version "1.9.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("application")
    `maven-publish`
}

group = AUTHOR
version = VERSION

repositories {
    mavenCentral()
    mavenLocal()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("com.google.code.gson:gson:2.11.0")
}

application {
    mainClass.set(MAIN_CLASS)
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = AUTHOR
            artifactId = PROJECT_NAME
            version = VERSION
            
            from(components["java"])
        }
    }
}