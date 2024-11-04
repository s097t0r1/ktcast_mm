package me.s097t0r1.ktcast.plugins.convention_plugin

import com.android.build.api.dsl.LibraryExtension
import me.s097t0r1.ktcast.plugins.implementation
import me.s097t0r1.ktcast.plugins.kapt
import me.s097t0r1.ktcast.plugins.module_configuration.ext.bundle
import me.s097t0r1.ktcast.plugins.module_configuration.ext.library
import me.s097t0r1.ktcast.plugins.module_configuration.ext.libraryExtension
import me.s097t0r1.ktcast.plugins.module_configuration.ext.libs
import me.s097t0r1.ktcast.plugins.module_configuration.ext.versionOf
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class FeatureImplConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {

        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
            apply("org.jetbrains.kotlin.kapt")
            apply("compose-configurator")
        }

        libraryExtension.configureAndroidExtension(target)

        dependencies {

            implementation(project(":core:di:library"))
            implementation(project(":core:exceptions:library"))
            implementation(project(":core:mvi:android-library"))
            implementation(project(":core:navigation:android-library"))
            implementation(project(":core:ui-components:android-library"))
            implementation(project(":core:dialog:android-library"))
            implementation(project(":libraries:viewmodel-factory:android-library"))
            implementation(project(":libraries:resource-provider:android-library"))
            implementation(project(":libraries:validator:android-library"))
            implementation(project(":libraries:either:library"))
            implementation(project(":libraries:mapper:library"))


            implementation(libs library "androidx.core.ktx")
            implementation(libs library "androidx.appcompat")
            implementation(libs library "androidx.fragment.ktx")
            implementation(libs library "androidx.lifecycle.viewmodel.ktx")
            implementation(libs library "androidx.lifecycle.runtime.ktx")
            implementation(libs bundle "androidx.compose")
            implementation(libs bundle "orbit.mvi")

            implementation(libs library "google.dagger" )
            kapt(libs library "google.dagger.compiler")
        }
    }

    private fun LibraryExtension.configureAndroidExtension(project: Project) {

        compileSdk = project.libs.versionOf("compileSdk").toInt()

        defaultConfig {
            targetSdk = project.libs.versionOf("targetSdk").toInt()
            minSdk = project.libs.versionOf("minSdk").toInt()
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        project.tasks.withType(KotlinCompile::class.java).configureEach {
            kotlinOptions {
                jvmTarget = "17"
            }
        }

    }
}