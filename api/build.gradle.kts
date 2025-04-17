plugins {
    `java-library`
}

projectConfiguration {
    artifactId.set("bttv4j-api")
    artifactDisplayName.set("Bttv4J API")
    artifactDescription.set("BetterTTV4J API")
}

dependencies {
    api(project(":common"))

    implementation(platform("com.fasterxml.jackson:jackson-bom:2.18.3"))
    implementation(group = "com.fasterxml.jackson.datatype", name = "jackson-datatype-jsr310")

    implementation(platform("io.github.openfeign:feign-bom:13.5"))
    implementation(group = "io.github.openfeign", name = "feign-core")
    implementation(group = "io.github.openfeign", name = "feign-jackson")
    implementation(group = "io.github.openfeign", name = "feign-slf4j")
    implementation(group = "io.github.openfeign", name = "feign-okhttp")

    implementation(platform("io.github.resilience4j:resilience4j-bom:2.3.0"))
    implementation(group = "io.github.resilience4j", name = "resilience4j-all")
    implementation(group = "io.github.resilience4j", name = "resilience4j-feign")
}
