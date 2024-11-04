package me.s097t0r1.ktcast.plugins.module_configuration.config

import com.android.build.gradle.BaseExtension
import me.s097t0r1.ktcast.plugins.module_configuration.ext.libs
import me.s097t0r1.ktcast.plugins.module_configuration.ext.versionOf
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun BaseExtension.configureCommonOptions(project: Project) {
    compileSdkVersion(project.libs.versionOf("compileSdk").toInt())

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
