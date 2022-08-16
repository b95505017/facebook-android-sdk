plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.facebook.login"
    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    defaultConfig {
        consumerProguardFiles("proguard-rules.pro")
    }
}

dependencies {
    with(projects.libraryProject.facebookAndroidSdk) {
        api(facebookCore)
        api(facebookCommon)
    }
    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.appcompat)
}
