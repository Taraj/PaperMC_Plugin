import com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
plugins {
    java
    val kotlinVersion: String by System.getProperties()
    kotlin("jvm").version(kotlinVersion)
    id("com.github.johnrengelman.shadow").version("5.2.0")
}
group = "tk.tarajki"
version = "1.0-SNAPSHOT"
repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}
dependencies {
    val kotlinVersion: String by System.getProperties()
    implementation(kotlin("stdlib", kotlinVersion))
    //https://papermc.io/javadocs/paper/1.16/overview-summary.html
    compileOnly("com.destroystokyo.paper", "paper-api", "1.16.1-R0.1-SNAPSHOT")
}
val autoRelocate by tasks.register<ConfigureShadowRelocation>("configureShadowRelocation", ConfigureShadowRelocation::class) {
    target = tasks.getByName("shadowJar") as ShadowJar?
    val packageName = "${project.group}.${project.name.toLowerCase()}"
    prefix = "$packageName.shaded"
}
tasks {
    shadowJar {
        archiveClassifier.set("")
        project.configurations.implementation.get().isCanBeResolved = true
        configurations = listOf(project.configurations.implementation.get())
        dependsOn(autoRelocate)
    }
    build {
        dependsOn(shadowJar)
    }
}