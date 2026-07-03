plugins {
    java
    `maven-publish`
    `java-library`
    checkstyle
    alias(libs.plugins.spotless)
    alias(libs.plugins.lombok)
    alias(libs.plugins.publishing)
}

group = "dev.minestom-united"
version = "0.0.3"

repositories {
    mavenCentral()
}

dependencies {
    api(libs.minestom)
    api(libs.logging.api)
    api(libs.common.config)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
    withSourcesJar()
}

checkstyle {
    configFile = rootProject.file("minestom-checks.xml")
    toolVersion = "10.21.4"
    isIgnoreFailures = false
}

spotless {
    java {
        // matches Checkstyle CustomImportOrder: STATIC###THIRD_PARTY_PACKAGE###STANDARD_JAVA_PACKAGE
        importOrder("\\#", "", "java", "javax")
        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
    }
}

mavenPublishing {
    coordinates("dev.minestom-united", "entrypoint", version as String?)

    publishToMavenCentral()
    signAllPublications()

    pom {
        name = "Entrypoint"
        description = "A lightweight abstraction over Minestom that streamlines server setup and reduces boilerplate for Minecraft server developers."
        url = "https://github.com/Minestom-United/entrypoint"

        licenses {
            license {
                name = "MIT"
                url = "https://github.com/Minestom-United/entrypoint/blob/master/LICENSE"
            }
        }

        developers {
            developer {
                id = "Foxikle"
                url = "https://github.com/Foxikle"
            }

            developer {
                id = "TropicalShadow"
                url = "https://github.com/TropicalShadow"
            }

            developer {
                id = "Webhead1104"
                url = "https://github.com/Webhead1104"
            }
        }

        issueManagement {
            system = "Github"
            url = "https://github.com/Minestom-United/entrypoint/issues"
        }

        scm {
            url.set("https://github.com/Minestom-United/entrypoint")
            connection.set("scm:git:git://github.com/Minestom-United/entrypoint.git")
            developerConnection.set("scm:git:git@github.com:Minestom-United/entrypoint.git")
        }
    }
}
