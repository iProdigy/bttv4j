plugins {
    `java-library`
    id("org.gradlex.extra-java-module-info") version "1.12"
}

projectConfiguration {
    artifactId.set("bttv4j-ws")
    artifactDisplayName.set("Bttv4J WS")
    artifactDescription.set("BetterTTV4J WebSocket")
}

dependencies {
    api(project(":common"))

    implementation(platform("com.fasterxml.jackson:jackson-bom:2.18.3"))
    implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind")
    implementation(group = "com.fasterxml.jackson.datatype", name = "jackson-datatype-jsr310")

    implementation(group = "com.squareup.okhttp3", name = "okhttp", version = "4.12.0")

    api(platform("com.github.philippheuer.events4j:events4j-bom:0.12.2"))
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
