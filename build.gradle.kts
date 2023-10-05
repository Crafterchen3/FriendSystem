group = "com.deckerpw"
version = "1.0-RV-B1"

plugins {
    kotlin("jvm") version "1.9.0"
    id("io.papermc.paperweight.userdev") version "1.5.5"
    id("xyz.jpenilla.run-paper") version "2.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    kotlin("stdlib")
    paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")
}

kotlin {
    jvmToolchain(17)
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(17)
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}