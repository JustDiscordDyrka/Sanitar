plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
}

group = "org.dyrka.sanitar.database"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    implementation("org.jetbrains.exposed", "exposed-core", "0.38.2")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.38.2")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.38.2")
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")
    implementation("io.insert-koin:koin-core:3.2.0")
    implementation("io.github.astonbitecode:j4rs:0.13.0")
}
