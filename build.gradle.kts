plugins {
    id("me.philippheuer.configuration") version "0.16.3"
}

version = properties["version"] as String

allprojects {
    apply(plugin = "me.philippheuer.configuration")

    repositories {
        mavenCentral()
    }

    projectConfiguration {
        language.set(me.philippheuer.projectcfg.domain.ProjectLanguage.JAVA)
        type.set(me.philippheuer.projectcfg.domain.ProjectType.LIBRARY)
        javaVersion.set(JavaVersion.VERSION_17)
        artifactGroupId.set("io.github.iprodigy.bttv")
        disablePluginModules = listOf("DependencyReport", "LombokFeature")

        pom = {
            it.url.set("https://github.com/iProdigy/bttv4j")
            it.issueManagement {
                system.set("GitHub")
                url.set("https://github.com/iProdigy/bttv4j/issues")
            }
            it.inceptionYear.set("2025")
            it.developers {
                developer {
                    id.set("iProdigy")
                    email.set("gitprodigy@proton.me")
                    roles.add("maintainer")
                }
            }
            it.licenses {
                license {
                    name.set("MPL-2.0")
                    distribution.set("repo")
                    url.set("https://github.com/iProdigy/bttv4j/blob/main/LICENSE")
                }
            }
            it.scm {
                connection.set("scm:git:https://github.com/iProdigy/bttv4j.git")
                developerConnection.set("scm:git:git@github.com:iProdigy/bttv4j.git")
                url.set("https://github.com/iProdigy/bttv4j")
            }
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform {
            includeTags("unittest")
            excludeTags("integration")
        }
    }
}
