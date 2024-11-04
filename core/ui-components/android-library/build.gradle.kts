plugins {
    id("ktcast-android-library")
    id("compose-configurator")
}

android {
    namespace = "me.s097t0r1.core.ui_components"
}

dependencies {

    implementation(projects.core.uiComponents.res)
    implementation(libs.bundles.androidx.compose)

}