plugins {
    java
    `maven-publish`
    `java-library`
    alias(libs.plugins.lombok)
}

group = "dev.minestomunited.entrypoint"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api(libs.minestom)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

publishing {
    repositories {
        maven {
            name = "MinestomUnitedRepository"
            val isSnapshot = version.toString().endsWith("-SNAPSHOT")
            url = uri(
                if (isSnapshot)
                    "https://repo.minestom-united.dev/snapshots"
                else "https://repo.minestom-united.dev/releases"
            )

            var u = System.getenv("REPO_USERNAME")
            var p = System.getenv("REPO_PASSWORD")

            if (u == null || u.isEmpty()) {
                u = "no-value-provided"
            }
            if (p == null || p.isEmpty()) {
                p = "no-value-provided"
            }

            val user = providers.gradleProperty("MinestomUnitedRepositoryUsername").orElse(u).get()
            val pass = providers.gradleProperty("MinestomUnitedRepositoryPassword").orElse(p).get()

            credentials {
                username = user
                password = pass
            }
            authentication {
                create<BasicAuthentication>("basic") {

                }
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            from(components["java"])

            pom {
                name = this@create.artifactId
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
                    }

                    developer {
                        id = "TropicalShadow"
                    }

                    developer {
                        id = "Webhead1104"
                    }
                }

                issueManagement {
                    system = "Github"
                    url = "https://github.com/Minestom-United/entrypoint/issues"
                }

                scm {
                    connection.set("scm:git:git://github.com/Minestom-United/entrypoint.git")
                    developerConnection.set("scm:git:git@github.com:Minestom-United/entrypoint.git")
                    url.set("https://github.com/Minestom-United/entrypoint")
                    tag.set("HEAD")
                }

                ciManagement {
                    system.set("Github Actions")
                    url.set("https://github.com/Minestom-United/entrypoint/actions")
                }
            }
        }
    }
}