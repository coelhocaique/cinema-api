import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

object DependencyVersions {
    const val SPRING_VERSION = "2.5.4"
}

plugins {
    application
    kotlin("jvm")
}

apply(plugin = "org.springframework.boot")
apply(plugin = "application")

application{
    mainClass.set("com.coelhocaique.cinema.api.ApiStarterKt")
}

tasks.getByName<Zip>("distZip").enabled = false
tasks.getByName<Tar>("distTar").enabled = false

dependencies {
    implementation(project(":cinema-core"))
    implementation("org.springframework.boot:spring-boot-starter-webflux:${DependencyVersions.SPRING_VERSION}")
    implementation("org.springframework.boot:spring-boot-starter-actuator:${DependencyVersions.SPRING_VERSION}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.5")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${DependencyVersions.SPRING_VERSION}")
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("io.rest-assured:rest-assured:4.4.0")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:1.5.10")
}

repositories {
    mavenCentral()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "16"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "16"
}