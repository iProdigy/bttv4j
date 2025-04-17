plugins {
    `java-library`
}

projectConfiguration {
    artifactId.set("bttv4j-common")
    artifactDisplayName.set("Bttv4J Common")
    artifactDescription.set("BetterTTV4J Common")
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
