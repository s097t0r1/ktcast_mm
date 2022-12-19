plugins {
    id("com.android.library")
    id("module-configurator")
    id("compose-configurator")
}

dependencies {

    implementation(projects.core.uiComponents.androidLibrary)
    implementation(projects.core.mvi.androidLibrary)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.bundles.androidx.compose)

}