plugins {
    //trick: for the same plugin versions in all sub-modules
    kotlin("multiplatform").version(libs.versions.kotlin).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    id("maven-publish")
}

tasks.register("clean", Delete::class) {
    delete(layout.buildDirectory)
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/nice-healthcare/typeform-kotlin")
            credentials {
                username = System.getenv("USERNAME")
                password = System.getenv("TOKEN")
            }
        }
    }

    publications {
        register<MavenPublication>("gpr") {
            groupId = "healthcare.nice"
            artifactId = "typeform"
            version = System.getenv().getOrDefault("VERSION", System.getProperties().getProperty("version"))
            artifact("${layout.projectDirectory}/typeform/build/outputs/aar/typeform-release.aar")
        }
    }
}

tasks.withType<PublishToMavenLocal> {
    dependsOn(":typeform:bundleReleaseAar")
}

tasks.withType<PublishToMavenRepository> {
    dependsOn(":typeform:bundleReleaseAar")
}
