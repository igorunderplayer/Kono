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
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("dev.kord:kord-core:0.8.2")
    implementation("ch.qos.logback:logback-core:1.4.6")
    implementation("ch.qos.logback:logback-classic:1.4.6")
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