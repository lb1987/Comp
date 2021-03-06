// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.6.2")
        //classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.31")
        classpath(kotlin("gradle-plugin", version = "1.3.71"))
        
        classpath("com.novoda:bintray-release:0.9.1")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        //maven("https://dl.bintray.com/lib/wip")
    }
}

task("clean") {
    delete(rootProject.buildDir)
}