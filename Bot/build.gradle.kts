plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
    id("java")
}

group = "org.dyrka.sanitar.bot"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io/")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    implementation("net.dv8tion:JDA:5.0.0-alpha.11")
    implementation("com.github.minndevelopment:jda-ktx:45f2776")
    implementation("io.insert-koin:koin-core:3.2.0")
    implementation("org.koin:koin-java:3.2.0")
}