import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
    kotlin("plugin.spring") version "1.5.21"
    id("org.springframework.boot") version "2.5.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0" apply true
    `java-library`
    `maven-publish`
}

group = "io.mikheevshow"
version = "1.0-SNAPSHOT"

publishing {
    publications {
        create<MavenPublication>("binance-spring-boot-starter") {
            from(components["java"])
        }
    }
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://nexus.pentaho.org/content/groups/omni/")
}

dependencies {
    api("org.apache.logging.log4j:log4j-api-kotlin:1.0.0")
    implementation("org.apache.logging.log4j:log4j-api:2.14.1")
    implementation("org.apache.logging.log4j:log4j-core:2.14.1")
    implementation("org.springframework.boot:spring-boot-starter")
    api("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.4")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.21")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
        incremental = false
    }
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar>().configureEach {
    this.enabled = false
}

tasks.jar {
    enabled = true
}

java {
    withSourcesJar()
}