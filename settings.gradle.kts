enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ktcast"

include(":app")

// DI
include(":core:di:library")

// Navigation
include(":core:navigation:android-library")

// MVVM
include(":core:mvvm:android-library")
include(":core:mvvm:android-utils")
include(":core:mvvm:res")

include(":core:exceptions:library")
include(":common:network:library")

include(":core:utils:mapper:library")
include(":core:utils:reaction:library")
include(":core:utils:viewmodel_factory:android_library")

include(":common:persistence:database:android-library")

include(":feature:pre_authorized_zone:splash:api")
include(":feature:pre_authorized_zone:splash:impl")
include(":feature:pre_authorized_zone:splash:res")
include(":core:ui-components:android-library")
include(":core:ui-components:res")