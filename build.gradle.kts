import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.7.21"
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
    implementation("dev.kord:kord-core:0.9.0")

    // Logging
    implementation("ch.qos.logback:logback-core:1.4.6")
    implementation("ch.qos.logback:logback-classic:1.4.6")

    // Mongo
    implementation("org.litote.kmongo:kmongo-coroutine:4.8.0")

    // Riot
    implementation("com.github.stelar7:R4J:2.2.15")

    // Ktor
    implementation("io.ktor:ktor-server-core-jvm:2.3.2")
    implementation("io.ktor:ktor-server-netty-jvm:2.3.2")
    implementation("io.ktor:ktor-server-freemarker:2.3.2")
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
        kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    }
}



application {
    mainClass.set("me.igorunderplayer.kono.Launcher")
}