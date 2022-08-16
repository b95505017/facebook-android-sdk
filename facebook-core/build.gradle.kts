plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.facebook.core"
    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    defaultConfig {
        consumerProguardFiles("proguard-rules.pro")
    }
    buildFeatures {
        buildConfig = true
        aidl = true
    }
}

dependencies {
    api(projects.libraryProject.facebookAndroidSdk.facebookBolts)
    implementation(libs.kotlin.stdlib)
    with(libs.androidx) {
        implementation(core.core)
        implementation(annotation)
    }
    implementation(libs.installReferrer)
    api("androidx.localbroadcastmanager:localbroadcastmanager:1.1.0")
}
