import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}
object DependencyVersions {
    const val SPRING_VERSION = "2.1.7.RELEASE"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive:${DependencyVersions.SPRING_VERSION}")
    implementation("org.springframework.boot:spring-boot-starter-webflux:${DependencyVersions.SPRING_VERSION}")
    testImplementation("io.projectreactor:reactor-test:3.2.11.RELEASE")
    testImplementation("io.projectreactor:reactor-test:3.2.11.RELEASE")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${DependencyVersions.SPRING_VERSION}")
    implementation(kotlin("stdlib-jdk8"))
}
repositories {
    mavenCentral()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}