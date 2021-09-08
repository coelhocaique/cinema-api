//import org.springframework.boot.gradle.tasks.run.BootRun
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

object DependencyVersions {
    const val SPRING_VERSION = "2.5.4"
}

plugins {
    application
    kotlin("jvm")
    java
}

apply(plugin = "org.springframework.boot")
apply(plugin = "application")

//tasks.getByName<BootRun>("bootRun") {
//    main = "com.coelhocaique.cinema.api.ApiStarterKt"
//    systemProperties(System.getProperties().mapKeys { it.key as String })
//}

application{
    mainClassName = "com.coelhocaique.cinema.api.ApiStarterKt"
}

tasks.getByName<Zip>("distZip").enabled = false
tasks.getByName<Tar>("distTar").enabled = false

dependencies {
    implementation(project(":cinema-core"))
    implementation("org.springframework.boot:spring-boot-starter-webflux:${DependencyVersions.SPRING_VERSION}")
    implementation("org.springframework.boot:spring-boot-starter-actuator:${DependencyVersions.SPRING_VERSION}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.9")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${DependencyVersions.SPRING_VERSION}")
    implementation(kotlin("stdlib-jdk8"))
}
repositories {
    mavenCentral()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "11"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "11"
}