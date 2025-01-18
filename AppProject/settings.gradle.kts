pluginManagement {
    repositories {
        google() // Ajoutez ici le repository Google
        mavenCentral() // Ajoutez ici le repository Maven Central
        gradlePluginPortal() // Vous pouvez garder ce repository si nécessaire
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google() // Ajoutez ici également le repository Google
        mavenCentral() // Ajoutez ici également Maven Central
    }
}

rootProject.name = "AppProject"
include(":app")
