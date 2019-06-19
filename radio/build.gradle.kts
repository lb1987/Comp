import com.novoda.gradle.release.PublishExtension
import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.library")
    id("com.novoda.bintray-release")
    kotlin("android")
    kotlin("kapt")
    kotlin("android.extensions")
}
android {
    compileSdkVersion(28)
    defaultConfig {
        minSdkVersion(23)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    dataBinding {
        isEnabled = true
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.0.2")
    implementation("androidx.core:core-ktx:1.0.2")
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-beta2")
    implementation("com.google.android.material:material:1.0.0")
    implementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))
    implementation(kotlin("reflect", KotlinCompilerVersion.VERSION))
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}

configure<PublishExtension> {
    repoName = "wip"
    userOrg = "lib"           //bintray注册的用户名
    groupId = "bai.wip"       //compile引用时的第1部分groupId
    artifactId = "radio"     //compile引用时的第2部分项目名
    publishVersion = "0.0.1"  //compile引用时的第3部分版本号
    desc = "radio"
    website = "https://github.com/lb1987/Comp"
}
// ./gradlew clean build bintrayUpload -PbintrayUser=lib -PbintrayKey=cf86642ec25f0a86047964447c1e37983d872fa1 -PdryRun=false
