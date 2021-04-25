import com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
plugins {
    java
    val kotlinVersion: String by System.getProperties()
    kotlin("jvm").version(kotlinVersion)
    id("com.github.johnrengelman.shadow").version("6.1.0")
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
    compileOnly("com.destroystokyo.paper", "paper-api", "1.16.5-R0.1-SNAPSHOT")
}
// This can be slower than manually relocating, if that's going to be an issue manually do so instead.
val autoRelocate by tasks.register<ConfigureShadowRelocation>("configureShadowRelocation", ConfigureShadowRelocation::class) {
    target = tasks.getByName("shadowJar") as ShadowJar?
    val packageName = "${project.group}.${project.name.toLowerCase()}"
    prefix = "$packageName.shaded"
}
tasks {
    val javaVersion = JavaVersion.VERSION_11.toString()
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions { jvmTarget = javaVersion }
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    shadowJar {
        archiveClassifier.set("")
        project.configurations.implementation.get().isCanBeResolved = true
        configurations = listOf(project.configurations.implementation.get())
        dependsOn(autoRelocate)
        minimize()
    }
    build { dependsOn(shadowJar) }
}
