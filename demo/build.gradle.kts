plugins {
    id("java")
    id("com.gradleup.shadow") version "9.4.1"
    application
}

group = "dev.minestomunited.entrypoint"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":"))
}

application {
    mainClass="dev.minestomunited.entrypoint.DemoMain"
}