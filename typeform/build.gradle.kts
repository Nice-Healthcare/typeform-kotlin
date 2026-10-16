import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.kmp.library)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.test.logger)
}

kotlin {
    android {
        namespace = "com.typeform"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
        androidResources {
            enable = true
        }
        withHostTest {}
        lint {
            checkReleaseBuilds = false
            checkDependencies = false
            checkTestSources = false
        }
    }

    jvmToolchain(21)

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.jetbrains.compose.resources)
                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.serialization.json)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.navigation.compose)
                implementation(libs.coil.compose.core)
                implementation(libs.jetbrains.compose.animation)
                implementation(libs.jetbrains.compose.foundation)
                implementation(libs.jetbrains.compose.material)
                implementation(libs.jetbrains.compose.material3)
                implementation(libs.jetbrains.compose.preview)
                implementation(libs.jetbrains.compose.runtime)
                implementation(libs.jetbrains.compose.ui)
                implementation(libs.jetbrains.compose.ui.tooling)
                implementation(libs.ktor.android)
                implementation(libs.ktor.client)
                implementation(libs.ktor.engine)
            }
        }

        getByName("androidHostTest") {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

compose {
    resources {
        generateResClass = always
        publicResClass = false
        packageOfResClass = "com.typeform.resources"
    }
}

ktlint {
    verbose = true
    outputToConsole = true
}
