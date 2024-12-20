pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            version("android.plugin", "8.7.0")
            version("compose", "1.7.1")
            version("kotlin", "2.1.0")

            plugin("androidLibrary", "com.android.library").versionRef("android.plugin")
            plugin("compose", "org.jetbrains.compose").versionRef("compose")
            plugin("composeCompiler", "org.jetbrains.kotlin.plugin.compose").versionRef("kotlin")
            plugin("kotlinMultiplatform", "org.jetbrains.kotlin.multiplatform").versionRef("kotlin")
            plugin("kotlinSerialization", "org.jetbrains.kotlin.plugin.serialization").versionRef("kotlin")
            plugin("testLogger", "com.adarshr.test-logger").version("3.2.0")
            plugin("ktlint", "org.jlleitschuh.gradle.ktlint").version("12.1.1")
        }

        create("tools") {
            version("gradle", "8.9")
        }
    }
}

rootProject.name = "typeform-kotlin"
include(":typeform")
