plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("application")
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
    implementation("net.dv8tion:JDA:5.0.0-alpha.13")
    implementation("com.github.minndevelopment:jda-ktx:d5c5d9d")
    implementation("io.github.cdimascio:dotenv-kotlin:6.3.1")
    implementation("io.insert-koin:koin-core:3.2.0")
    implementation(project(":Bot"))
    implementation("com.google.guava:guava:31.1-jre")
    implementation("org.mapdb:mapdb:3.0.8")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.dyrka.sanitar.core.MainKt"
    }
}

application {
    mainClass.set("org.dyrka.sanitar.core.MainKt")
}