plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.facebook.bolts"
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
    implementation(libs.kotlin.stdlib)
    with(libs.androidx) {
        implementation(core.core)
        implementation(annotation)
    }
}
