import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.4" apply false
    kotlin("plugin.spring") version "1.5.21"
    kotlin("jvm") version "1.5.21"
    id("jacoco")
}

repositories {
    mavenCentral()
}

subprojects{
    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "jacoco")

    group = "com.coelhocaique"
    version = "0.0.1-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_11
    java.targetCompatibility = JavaVersion.VERSION_11

    val developmentOnly by configurations.creating
    configurations {
        runtimeClasspath {
            extendsFrom(developmentOnly)
        }
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.21")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.21")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.5.2")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.5.2")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.0.pr3")
        implementation("com.fasterxml.jackson.module:jackson-module-parameter-names:2.10.0.pr3")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.10.0.pr3")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.10.0.pr3")

        testImplementation("org.junit.jupiter:junit-jupiter:5.5.1")
        testImplementation("io.mockk:mockk:1.9.3")
        testImplementation("io.projectreactor:reactor-test:3.2.11.RELEASE")
    }

    java.sourceCompatibility = JavaVersion.VERSION_11
    java.targetCompatibility = JavaVersion.VERSION_11

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    val excludedClassesFromCoverageAnalysis = listOf(
            "CoreConfiguration",
            "Starter",
            "Config",
            "Scheduler",
            "Repository",
            "Exception",
            "Constants",
            "Routing",
            "Filter"
    ).map { "**/*${it}**" }

    tasks.test {
        useJUnitPlatform()
        reports.junitXml.isEnabled = true
        finalizedBy(tasks.jacocoTestReport)
    }

    tasks.jacocoTestReport {
        dependsOn(tasks.test)

        reports {
            xml.isEnabled = true
            csv.isEnabled = false
            html.isEnabled = true
            html.destination = file("${buildDir}/jacocoTestReport")
        }
    }

    afterEvaluate {
        tasks.jacocoTestReport {
            classDirectories.setFrom(files(classDirectories.files.map {
                fileTree("dir" to it,
                        "excludes" to excludedClassesFromCoverageAnalysis
                )
            }))
        }
    }
}