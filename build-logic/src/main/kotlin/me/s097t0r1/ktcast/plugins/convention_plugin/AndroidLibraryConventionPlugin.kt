package me.s097t0r1.ktcast.plugins.convention_plugin

import com.android.build.api.dsl.LibraryExtension
import me.s097t0r1.ktcast.plugins.module_configuration.ext.libraryExtension
import me.s097t0r1.ktcast.plugins.module_configuration.ext.libs
import me.s097t0r1.ktcast.plugins.module_configuration.ext.versionOf
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
        }
        libraryExtension.configure(target)
    }

    private fun LibraryExtension.configure(project: Project) {

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