plugins {
    `java-library`
    id("me.philippheuer.configuration") version "0.15.2"
}

version = properties["version"] as String

allprojects {
    apply(plugin = "me.philippheuer.configuration")
    apply(plugin = "java-library")

    repositories {
        mavenCentral()
    }

    projectConfiguration {
        language.set(me.philippheuer.projectcfg.domain.ProjectLanguage.JAVA)
        type.set(me.philippheuer.projectcfg.domain.ProjectType.LIBRARY)
        javaVersion.set(JavaVersion.VERSION_17)
        artifactGroupId.set("io.github.iprodigy.bttv")
        javadocLombok.set(false)

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

    dependencies {
        api(group = "org.jspecify", name = "jspecify", version = "1.0.0")

        implementation(platform("org.slf4j:slf4j-bom:2.0.17"))
        api(group = "org.slf4j", name = "slf4j-api")
        testImplementation(group = "org.slf4j", name = "slf4j-simple")

        implementation(platform("com.fasterxml.jackson:jackson-bom:2.18.3"))
        implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind")
        implementation(group = "com.fasterxml.jackson.datatype", name = "jackson-datatype-jsr310")

        implementation(group = "com.squareup.okhttp3", name = "okhttp", version = "4.12.0")
    }

    tasks.test {
        useJUnitPlatform {
            includeTags("unittest")
            excludeTags("integration")
        }
    }
}
