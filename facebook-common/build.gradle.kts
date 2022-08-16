plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.facebook.common"
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
    api(projects.libraryProject.facebookAndroidSdk.facebookCore)
    implementation(libs.kotlin.stdlib)
    with(libs.androidx) {
        implementation(appcompat)
        implementation(browser)
        implementation(activity.lib)
        implementation(fragment.lib)
    }
    implementation(libs.zxing)
    implementation(libs.mdc)
}
