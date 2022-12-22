import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    id("org.sonarqube") version "3.4.0.2513"
    // Causes error in Doker build
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
}

group = "de.neocargo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")
    implementation("org.springframework.data:spring-data-mongodb:3.1.1")
    implementation("org.springframework:spring-web:5.3.23")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.5")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.3")
    implementation("org.springdoc:springdoc-openapi-data-rest:1.6.0")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.0")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.6.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server:2.7.5")
//    // To be removed once all code is translated to Kotlin
//    compileOnly("org.projectlombok:lombok:1.18.24")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-oauth2-client
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client:3.0.0")
    implementation("org.springframework.security.oauth.boot:spring-security-oauth2-autoconfigure:2.6.8")


}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
