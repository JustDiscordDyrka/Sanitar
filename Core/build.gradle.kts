plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
}

group = "org.dyrka.sanitar.core"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    implementation("io.github.cdimascio:dotenv-kotlin:6.3.1")
    implementation("io.insert-koin:koin-core:3.2.0")
    implementation("org.koin:koin-java:3.2.0")
    implementation(project(":Bot"))
}
