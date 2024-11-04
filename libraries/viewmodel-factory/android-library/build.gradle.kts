plugins {
    id("com.android.library")
    id("module-configurator")
    id("kotlin-kapt")
}

android {
    namespace = "me.s097t0r1.ktcast.libraries.factory"
}

dependencies {

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.google.dagger)
    kapt(libs.google.dagger.compiler)

}