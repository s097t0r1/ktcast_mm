plugins {
    id("com.android.library")
    id("compose-configurator")
    id("module-configurator")
}

android {
    buildFeatures {
        viewBinding = true
    }
    namespace = "me.s097t0r1.core.mvi"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.compose.viewbinding)
    implementation(libs.bundles.androidx.compose)
    implementation(libs.bundles.orbit.mvi)

    implementation(projects.core.mvi.res)
    implementation(projects.core.uiComponents.androidLibrary)
    implementation(projects.core.exceptions.library)
    implementation(projects.core.uiComponents.res)
    implementation(projects.core.navigation.androidLibrary)
    implementation(projects.common.logout.androidLibrary)

}
