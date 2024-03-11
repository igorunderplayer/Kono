import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    application
}

group = "me.igorunderplayer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

    maven(
        url = "https://jitpack.io"
    )
}

dependencies {
    testImplementation(kotlin("test"))

    implementation(kotlin("stdlib-jdk8"))

    // Discord
    implementation("dev.kord:kord-core:0.13.1")

    // Logging
    implementation("ch.qos.logback:logback-core:1.4.14")
    implementation("ch.qos.logback:logback-classic:1.4.14")

    // Mongo
    implementation("org.litote.kmongo:kmongo-coroutine:4.11.0")

    // Riot
    implementation("com.github.stelar7:R4J:2.4.5")

    // Ktor
    implementation("io.ktor:ktor-server-core-jvm:2.3.7")
    implementation("io.ktor:ktor-server-netty-jvm:2.3.7")
    implementation("io.ktor:ktor-server-freemarker:2.3.7")
}

tasks {
    test {
        useJUnitPlatform()
    }

    build {
        mustRunAfter("clean", "test")
    }

    withType<ShadowJar> {
        archiveFileName.set("KonoBot.jar")
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_19.toString()
    }
}



application {
    mainClass.set("me.igorunderplayer.kono.Launcher")
}