plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.10"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    id("org.jlleitschuh.gradle.ktlint-idea") version "10.3.0"
    id("org.springframework.boot") version "2.7.1"
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
    implementation("io.github.cdimascio:dotenv-kotlin:6.3.1")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
    implementation("com.azure:azure-digitaltwins-core:1.2.4")
    implementation("com.azure:azure-identity:1.5.3")
    implementation("org.litote.kmongo:kmongo:4.6.1")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
}

application {
    mainClass.set("swc.microservice.mission.MissionMicroserviceKt")
}

jacoco {
    toolVersion = "0.8.7"
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
