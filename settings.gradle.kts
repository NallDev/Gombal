pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Gombal"
include(":app")
include(":app:onboarding")
include(":core")
include(":home")
include(":home:data")
include(":home:domain")
include(":detail")
include(":favorites")
include(":favorites:data")
include(":favorites:domain")
