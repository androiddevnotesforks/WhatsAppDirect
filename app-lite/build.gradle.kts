plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.devtech.whatsappdirect.app.lite"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.devtech.whatsappdirect.app.lite"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(projects.whatsAppDirect.core)
}