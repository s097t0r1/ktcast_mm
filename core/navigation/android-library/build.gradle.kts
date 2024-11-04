plugins {
    id("com.android.library")
    id("module-configurator")
}

android {
    namespace = "me.s097t0r1.core.navigation"
}

dependencies {

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment.ktx)
}
