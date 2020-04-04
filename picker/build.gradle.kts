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
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(23)
        targetSdkVersion(29)
        versionCode = 3
        versionName = "0.0.3"

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
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core-ktx:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("com.google.android.material:material:1.1.0")
    implementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))
    implementation(kotlin("reflect", KotlinCompilerVersion.VERSION))
    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}

configure<PublishExtension> {
    repoName = "wip"
    userOrg = "lib"           //bintray注册的用户名
    groupId = "bai.wip"       //compile引用时的第1部分groupId
    artifactId = "picker"     //compile引用时的第2部分项目名
    publishVersion = "0.0.2"  //compile引用时的第3部分版本号
    desc = "picker"
    website = "https://github.com/lb1987/Comp"
}
// ./gradlew clean build bintrayUpload -PbintrayUser=lib -PbintrayKey=cf86642ec25f0a86047964447c1e37983d872fa1 -PdryRun=false
