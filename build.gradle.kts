plugins {
    `java-library`
}

group = "dev.minestomunited.entrypoint"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    api(libs.minestom)

    implementation(libs.lombok)
    annotationProcessor(libs.lombok)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

tasks{

    compileJava{
        options.encoding = "UTF-8"
    }
}