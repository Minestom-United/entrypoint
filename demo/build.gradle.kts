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
}

dependencies {
    implementation(project(":"))
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

application {
    mainClass = "dev.minestomunited.entrypoint.DemoMain"
}
