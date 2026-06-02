plugins {
    id("java")
    alias(libs.plugins.lombok)
    alias(libs.plugins.shadow)
    application
}

group = "dev.minestomunited.entrypoint"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.minestom-united.dev/releases")
    maven("https://repo.minestom-united.dev/snapshots")
}

dependencies {
    implementation(project(":"))
    implementation(libs.logging.impl)
}

application {
    mainClass = "dev.minestomunited.entrypoint.DemoMain"
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}
