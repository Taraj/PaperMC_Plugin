rootProject.name = settings.extra["archives_base_name"] as String
pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        kotlin("jvm").version(settings.extra["kotlin_version"] as String)
        id("com.gradleup.shadow").version(settings.extra["shadow_version"] as String)
    }
}