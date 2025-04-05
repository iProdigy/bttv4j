plugins {
    `java-library`
}

projectConfiguration {
    artifactId.set("bttv4j-ws")
    artifactDisplayName.set("Bttv4J WS")
    artifactDescription.set("BetterTTV4J WebSocket")
}

dependencies {
    api(project(":common"))

    api(platform("com.github.philippheuer.events4j:events4j-bom:0.12.2"))
    api(group = "com.github.philippheuer.events4j", name = "events4j-api")
    implementation(group = "com.github.philippheuer.events4j", name = "events4j-core")
    implementation(group = "com.github.philippheuer.events4j", name = "events4j-handler-simple")
}
