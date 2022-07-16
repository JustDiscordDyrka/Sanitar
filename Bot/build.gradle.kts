plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    id("java")
}

group = "org.dyrka.sanitar.bot"
version = "1.0"

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

repositories {
    mavenCentral()
    maven("https://jitpack.io/")
    maven("https://m2.dv8tion.net/releases")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    implementation("net.dv8tion:JDA:5.0.0-alpha.13")
    implementation("com.github.minndevelopment:jda-ktx:d5c5d9d")
    implementation("io.insert-koin:koin-core:3.2.0")
    implementation("com.sedmelluq:lavaplayer:1.3.78")
    implementation(project(":DataBaseAPI"))
    implementation("com.google.guava:guava:31.1-jre")
}