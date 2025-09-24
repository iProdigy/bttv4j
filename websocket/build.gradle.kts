plugins {
    `java-library`
    id("org.gradlex.extra-java-module-info") version "1.13.1"
}

projectConfiguration {
    artifactId.set("bttv4j-ws")
    artifactDisplayName.set("Bttv4J WS")
    artifactDescription.set("BetterTTV4J WebSocket")
}

dependencies {
    api(project(":common"))

    implementation(platform(libs.jackson.bom))
    implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind")
    implementation(group = "com.fasterxml.jackson.datatype", name = "jackson-datatype-jsr310")

    implementation(libs.okhttp)

    api(platform(libs.events4j.bom))
    api(group = "com.github.philippheuer.events4j", name = "events4j-api")
    implementation(group = "com.github.philippheuer.events4j", name = "events4j-core")
    implementation(group = "com.github.philippheuer.events4j", name = "events4j-handler-simple")
}

extraJavaModuleInfo {
    skipLocalJars.set(true)

    automaticModule("org.jetbrains.kotlin:kotlin-stdlib-common", "kotlin.stdlib")
    automaticModule("org.jetbrains:annotations", "org.jetbrains.annotations")

    module("com.github.philippheuer.events4j:events4j-api", "events4j.api") {
        exports("com.github.philippheuer.events4j.api")
        exports("com.github.philippheuer.events4j.api.domain")
        exports("com.github.philippheuer.events4j.api.service")
        requiresStatic("org.jspecify")
    }

    module("com.github.philippheuer.events4j:events4j-core", "events4j.core") {
        exports("com.github.philippheuer.events4j.core")
        exports("com.github.philippheuer.events4j.core.domain")
        exports("com.github.philippheuer.events4j.core.services")
        requiresTransitive("events4j.api")
        requiresStatic("org.jspecify")
    }

    module("com.github.philippheuer.events4j:events4j-handler-simple", "events4j.handler.simple") {
        exports("com.github.philippheuer.events4j.simple")
        exports("com.github.philippheuer.events4j.simple.domain")
        exports("com.github.philippheuer.events4j.simple.util")
        requiresTransitive("events4j.api")
        requiresStatic("org.jspecify")
    }
}
