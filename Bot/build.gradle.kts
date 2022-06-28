plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
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
    implementation("net.dv8tion:JDA:5.0.0-alpha.11")
    implementation("com.github.minndevelopment:jda-ktx:45f2776")
    implementation("io.insert-koin:koin-core:3.2.0")
    implementation("com.sedmelluq:lavaplayer:1.3.78")
    implementation("org.jetbrains.exposed", "exposed-core", "0.38.2")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.38.2")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.38.2")
    implementation(project(":DataBaseAPI"))
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")
    implementation("io.github.astonbitecode:j4rs:0.13.0")
}