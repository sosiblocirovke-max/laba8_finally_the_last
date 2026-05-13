// Репозитории для плагинов Gradle (Google, Maven Central, Plugin Portal).
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
// Автовыбор JDK через Foojay (удобно на чистых CI/машинах).
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
// Единые репозитории для всех модулей; запрет repo в подпроектах.
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

// Имя корневого проекта в IDE и артефактах — TodoApp.
rootProject.name = "TodoApp"
include(":app")
