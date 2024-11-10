plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.rangolidetector"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.rangolidetector"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.tensorflow.lite)
    implementation(libs.tensorflow.lite.task.vision)
    implementation(libs.ar.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.rendering)
    implementation(libs.sceneform.core)
    implementation(libs.sceneform.ux)
    implementation(libs.ui)
    implementation(libs.androidx.foundation)
    implementation(libs.material3)
    testImplementation(libs.junit)
}