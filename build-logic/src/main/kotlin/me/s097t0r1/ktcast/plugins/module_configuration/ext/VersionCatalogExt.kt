package me.s097t0r1.ktcast.plugins.module_configuration.ext

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal val Project.libs
    get() = this.extensions
        .getByType<VersionCatalogsExtension>()
        .named("libs")

internal infix fun VersionCatalog.library(alias: String) = findLibrary(alias).get()

internal infix fun VersionCatalog.bundle(alias: String) = findBundle(alias).get()

internal fun VersionCatalog.versionOf(name: String) =
    this.findVersion(name).get().requiredVersion

internal fun VersionCatalog.plugin(name: String) =
    this.findPlugin(name).get().get().pluginId
