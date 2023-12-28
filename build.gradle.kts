plugins {
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
}
buildscript {
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.2")
        classpath("com.github.dcendents:android-maven-gradle-plugin:1.4.1")
    }
}

allprojects {
    repositories {
        maven {
            url = uri("https://jitpack.io")
        }
        mavenCentral()
        maven {
            url = uri("https://maven.google.com")
        }
        google()
    }
}