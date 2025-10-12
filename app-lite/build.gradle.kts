import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}


val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}
fun getEnvOrLocal(key: String): String? {
    return System.getenv(key) ?: localProperties[key]?.toString()
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

    signingConfigs {
        create("release") {
            storeFile = file("../keystore.jks")
            storePassword = getEnvOrLocal("KEYSTORE_PASSWORD")
            keyAlias = getEnvOrLocal("KEY_ALIAS")
            keyPassword = getEnvOrLocal("KEY_PASSWORD")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(projects.whatsAppDirect.core)
}