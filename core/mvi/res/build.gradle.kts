plugins {
    id("com.android.library")
    id("compose-configurator")
    id("module-configurator")
}

android {
    namespace = "me.s097t0r1.core.mvi.res"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.bundles.androidx.compose)
}