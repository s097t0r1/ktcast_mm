plugins {
    alias(libs.plugins.detekt)
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.compose.compiler) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.android.gradle.plugin)
    }
}


allprojects {

    repositories {
        google()
        mavenCentral()
    }

    configureDetekt()

}

fun Project.configureDetekt() {

    val reportMerge by tasks.registering(io.gitlab.arturbosch.detekt.report.ReportMergeTask::class) {
        output.set(rootProject.buildDir.resolve("reports/detekt/merge.sarif"))
    }

    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        source = files("src/main/java", "src/main/kotlin")
        parallel = true
        buildUponDefaultConfig = true
        config = files(
            rootProject.file("config/detekt.yml"),
            rootProject.file("config/detekt-compose.yml")
        )

        dependencies {
            detektPlugins(rootProject.libs.kode.detekt.rules.compose)
            detektPlugins(rootProject.libs.arturbosch.detekt.formatting)
        }

    }

    plugins.withType(io.gitlab.arturbosch.detekt.DetektPlugin::class) {
        tasks.withType(io.gitlab.arturbosch.detekt.Detekt::class) detekt@{
            finalizedBy(reportMerge)
            reportMerge.configure {
                input.from(this@detekt.sarifReportFile)
            }
        }
    }
}
