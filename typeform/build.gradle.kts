plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.test.logger)
}

kotlin {
    androidTarget {
    }

    jvmToolchain(21)

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.compose.resources)
                implementation(libs.kotlinx.serialization.json)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.activity)
                implementation(libs.androidx.animation)
                implementation(libs.androidx.foundation)
                implementation(libs.androidx.navigation)
                implementation(libs.androidx.material)
                implementation(libs.androidx.material3)
                implementation(libs.androidx.runtime)
                implementation(libs.androidx.ui)
                implementation(libs.androidx.ui.graphics)
                implementation(libs.androidx.ui.preview)
                implementation(libs.androidx.ui.tooling)
                implementation(libs.coil.compose.core)
                implementation(libs.ktor.android)
                implementation(libs.ktor.client)
                implementation(libs.ktor.engine)
            }
        }

        val androidUnitTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "com.typeform"
    compileSdk = 36
    defaultConfig {
        minSdk = 31
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_21)
        targetCompatibility(JavaVersion.VERSION_21)
    }
    buildFeatures {
        compose = true
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
    lint {
        checkReleaseBuilds = false
        checkDependencies = false
        checkTestSources = false
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
    verbose.set(true)
    outputToConsole.set(true)
}
