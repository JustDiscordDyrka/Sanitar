import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import java.io.ByteArrayOutputStream;

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "org.dyrka.sanitar.core"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io/")
    maven("https://m2.dv8tion.net/releases")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    implementation("net.dv8tion:JDA:5.0.0-alpha.11")
    implementation("com.github.minndevelopment:jda-ktx:45f2776")
    implementation("io.github.cdimascio:dotenv-kotlin:6.3.1")
    implementation("io.insert-koin:koin-core:3.2.0")
    implementation(project(":Bot"))
    implementation("org.jetbrains.exposed", "exposed-core", "0.38.2")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.38.2")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.38.2")
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")
    implementation("io.github.astonbitecode:j4rs:0.13.0")
    implementation("com.google.guava:guava:31.1-jre")
}

tasks.register("buildRelease") {
    val byteOut = ByteArrayOutputStream()
    project.exec {
        workingDir = file("./")
        commandLine = "cargo build --release --manifest-path=${System.getProperty("user.dir")}/database_rs/Cargo.toml".split(" ")
        standardOutput = byteOut
    }

    println(String(byteOut.toByteArray()).trim())

    dependsOn(tasks.withType<ShadowJar>())
    dependsOn(tasks.named("copyNatives"))
}

tasks.register<Copy>("copyNatives") {
    from(file("${System.getProperty("user.dir")}/database_rs/target/release/libdatabase_rs${if (System.getProperty("os.name") == "Windows") ".dll" else ".so"}"))
    into("${System.getProperty("user.dir")}/Core/src/main/resources/")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.dyrka.sanitar.core.MainKt"
    }
}