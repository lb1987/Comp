import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "bai.wip.app"
        minSdkVersion(25)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "0.0.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

}

androidExtensions {
    isExperimental = true
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.0.2")
    implementation("androidx.core:core-ktx:1.0.2")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("com.google.android.material:material:1.0.0")
    implementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))
    implementation(kotlin("reflect", KotlinCompilerVersion.VERSION))

    testImplementation("junit:junit:4.12")

    // test
    androidTestImplementation("androidx.test:core:1.2.0")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test:rules:1.2.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.ext:truth:1.2.0")
    androidTestImplementation("com.google.truth:truth:0.42")

    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-accessibility:3.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-web:3.2.0")
    androidTestImplementation("androidx.test.espresso.idling:idling-concurrent:3.2.0")

    implementation(project(path = ":dialog"))
    implementation(project(path = ":picker"))
    implementation(project(path = ":radio"))
    //implementation("bai.wip:picker:0.0.2")
    //implementation("org.jetbrains.anko:anko:0.10.8")

}
