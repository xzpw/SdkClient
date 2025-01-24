plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.prinum.sdkclient"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.prinum.sdkclient"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(files("libs/sdk-debug.aar"))

    // This is necessarily dependency to maintain basic work with Compose
    implementation(libs.compose.material)
    // When we use jetpack navigation
    implementation(libs.compose.kmp.navigation)

    // Basic java project dependency
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}