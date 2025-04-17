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

    implementation(platform(libs.jackson.bom))
    implementation(group = "com.fasterxml.jackson.datatype", name = "jackson-datatype-jsr310")

    implementation(platform(libs.feign.bom))
    implementation(group = "io.github.openfeign", name = "feign-core")
    implementation(group = "io.github.openfeign", name = "feign-jackson")
    implementation(group = "io.github.openfeign", name = "feign-slf4j")
    implementation(group = "io.github.openfeign", name = "feign-okhttp")

    implementation(platform(libs.resilience4j.bom))
    implementation(group = "io.github.resilience4j", name = "resilience4j-all")
    implementation(group = "io.github.resilience4j", name = "resilience4j-feign")
}
