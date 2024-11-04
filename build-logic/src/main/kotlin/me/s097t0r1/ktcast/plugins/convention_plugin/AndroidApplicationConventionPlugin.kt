package me.s097t0r1.ktcast.plugins.convention_plugin

import com.android.build.api.dsl.ApplicationExtension
import me.s097t0r1.ktcast.plugins.implementation
import me.s097t0r1.ktcast.plugins.kapt
import me.s097t0r1.ktcast.plugins.module_configuration.ext.applicationExtension
import me.s097t0r1.ktcast.plugins.module_configuration.ext.bundle
import me.s097t0r1.ktcast.plugins.module_configuration.ext.library
import me.s097t0r1.ktcast.plugins.module_configuration.ext.libs
import me.s097t0r1.ktcast.plugins.module_configuration.ext.versionOf
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {

        with(pluginManager) {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
            apply("org.jetbrains.kotlin.kapt")
        }

        applicationExtension.configureAndroidExtension(target)

        dependencies {

            // Glue all modules
            glueAllProjects(project.parent?.subprojects ?: emptySet())

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

    private fun DependencyHandlerScope.glueAllProjects(allprojects: Set<Project>) {
        for (project in allprojects) {
            // Implement only leaf of project tree
            if (project.subprojects.isNotEmpty()) {
                glueAllProjects(project.subprojects)
            } else {
                // Prevent circular dependency of ":app" project
                if (project.name == "app") continue

                implementation(project)
            }
        }
    }

    private fun ApplicationExtension.configureAndroidExtension(project: Project) {

        compileSdk = project.libs.versionOf("compileSdk").toInt()

        buildFeatures {
            viewBinding = true
        }

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

