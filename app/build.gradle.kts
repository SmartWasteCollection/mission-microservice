plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.8.21"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.21"
    id("org.jlleitschuh.gradle.ktlint") version "11.1.0"
    id("org.jlleitschuh.gradle.ktlint-idea") version "11.1.0"
    id("org.springframework.boot") version "3.0.6"
    jacoco
    application
}

repositories {
    mavenCentral()
}

val kotestVersion: String by project
val jacksonVersion: String by project

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
    implementation("org.springframework.boot:spring-boot-starter-web:3.0.6")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")
    implementation("com.azure:azure-digitaltwins-core:1.3.8")
    implementation("com.azure:azure-identity:1.5.3")
    implementation("org.litote.kmongo:kmongo:4.9.0")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
}

application {
    mainClass.set("swc.microservice.mission.MissionMicroserviceKt")
}

jacoco {
    toolVersion = "0.8.10"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}
