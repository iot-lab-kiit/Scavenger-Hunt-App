pluginManagement {
    repositories {
        google()
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

rootProject.name = "Scavenger Hunt"
include(":app")
include(":authorization")
include(":onBoarding")
include(":teamBuilding")
include(":core:design")
include(":core:qrCode")
include(":dashboard")
include(":leaderBoard")
include(":playGame")
include(":core:network")
