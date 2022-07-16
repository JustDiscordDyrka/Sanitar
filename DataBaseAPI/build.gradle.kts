plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
}

group = "org.dyrka.sanitar.database"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    implementation("io.insert-koin:koin-core:3.2.0")
    implementation("io.github.astonbitecode:j4rs:0.13.0")
    implementation("com.google.guava:guava:31.1-jre")
}
