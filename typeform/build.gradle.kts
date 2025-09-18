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

    jvmToolchain(17)

    sourceSets {
        val commonMain by getting {
            dependencies {
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
                api(compose.animation)
                api(compose.foundation)
                api(compose.material)
                api(compose.materialIconsExtended)
                api(compose.material3)
                api(compose.preview)
                api(compose.runtime)
                api(compose.ui)
                api(compose.uiTooling)

                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.navigation.compose)
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
    compileSdk = 35
    defaultConfig {
        minSdk = 28
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_17)
        targetCompatibility(JavaVersion.VERSION_17)
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

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
}
