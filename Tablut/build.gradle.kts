import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * Learn how to create Gradle builds at https://guides.gradle.org/creating-new-gradle-builds
 */

plugins {
    `java-library`
    kotlin("jvm") version "1.3.60"
}

apply(plugin = "java-library")
apply(plugin = "kotlin")

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2")
    implementation("com.google.code.gson:gson:2.2.2")
    implementation("com.googlecode.aima-java:aima-core:3.0.0")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")
    testImplementation("org.slf4j:slf4j-jdk14:1.7.12")
}

repositories {
    mavenCentral()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs = options.compilerArgs + listOf("-Werror", "-Xlint:unchecked")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjvm-default=enable") // Enable default methods in Kt interfaces
        allWarningsAsErrors = true
    }
}

tasks.withType<Test> {
    testLogging {
        events("passed", "skipped", "failed", "standardError")
        exceptionFormat = TestExceptionFormat.FULL
    }
    useJUnitPlatform()
}

tasks.register<Jar>("fatJar") {
    dependsOn(subprojects.map { it.tasks.withType<Jar>() })
    manifest {
        attributes(mapOf(
            "Implementation-Title" to "TablutAIClient",
            "Implementation-Version" to rootProject.version,
            "Main-Class" to "it.unibo.ai.didattica.competition.tablut.aiclient.TablutAIClient"
        ))
    }
    archiveBaseName.set("TablutAIClient")
    isZip64 = true
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }) {
        // remove all signature files
        exclude("META-INF/")
        exclude("build")
        exclude(".gradle")
        exclude("build.gradle.kts")
        exclude("gradle")
        exclude("gradlew")
        exclude("gradlew.bat")
        exclude("settings.gradle.kts")
    }
    with(tasks.jar.get() as CopySpec)
}