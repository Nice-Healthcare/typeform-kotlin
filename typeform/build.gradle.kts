plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose") version "1.6.1"
    id("com.adarshr.test-logger") version "3.2.0"
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }

            publishLibraryVariants("release")
        }
    }

    sourceSets {
        val commonMain by getting {
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.animation)
                api(compose.material)
                api(compose.materialIconsExtended)
                api(compose.ui)
                api(compose.uiTooling)
                api(compose.preview)
                implementation("androidx.compose.material3:material3:1.2.0")
                implementation("androidx.navigation:navigation-compose:2.7.7")
                implementation("io.coil-kt:coil-compose:2.4.0")
            }
        }

        val androidUnitTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")
                implementation("com.squareup.moshi:moshi-kotlin:1.15.0")
            }
        }
    }
}

android {
    namespace = "com.typeform"
    compileSdk = 34
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}
