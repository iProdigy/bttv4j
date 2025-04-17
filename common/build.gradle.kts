plugins {
    `java-library`
}

projectConfiguration {
    artifactId.set("bttv4j-common")
    artifactDisplayName.set("Bttv4J Common")
    artifactDescription.set("BetterTTV4J Common")
}

dependencies {
    api(libs.jspecify)

    implementation(platform(libs.slf4j.bom))
    api(group = "org.slf4j", name = "slf4j-api")
    testImplementation(group = "org.slf4j", name = "slf4j-simple")

    implementation(platform(libs.jackson.bom))
    implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind")
    implementation(group = "com.fasterxml.jackson.datatype", name = "jackson-datatype-jsr310")

    implementation(libs.okhttp)
}
