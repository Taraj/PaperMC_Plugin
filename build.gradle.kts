import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
plugins {
    java
    kotlin("jvm")
    id("com.gradleup.shadow")
}
base.archivesName = project.extra["archives_base_name"] as String
version = project.extra["plugin_version"] as String
group = project.extra["plugin_group"] as String
repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") { name = "papermc" }
}
dependencies {
    implementation(kotlin("stdlib", project.extra["kotlin_version"] as String))
    compileOnly("io.papermc.paper", "paper-api", project.extra["paper_api_version"] as String)
}
tasks {
    val javaVersion = JavaVersion.toVersion((project.extra["java_version"] as String).toInt())
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        sourceCompatibility = javaVersion.toString()
        targetCompatibility = javaVersion.toString()
        options.release = javaVersion.toString().toInt()
    }
    withType<JavaExec>().configureEach { defaultCharacterEncoding = "UTF-8" }
    withType<Javadoc>().configureEach { options.encoding = "UTF-8" }
    withType<Test>().configureEach { defaultCharacterEncoding = "UTF-8" }
    withType<KotlinCompile>().configureEach {
        compilerOptions {
            extraWarnings = true
            jvmTarget = JvmTarget.valueOf("JVM_$javaVersion")
        }
    }
    java {
        toolchain.languageVersion = JavaLanguageVersion.of(javaVersion.toString())
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    jar {
        manifest.attributes["paperweight-mappings-namespace"] = project.extra["mappings"] as String
    }
    shadowJar {
        manifest.attributes["paperweight-mappings-namespace"] = project.extra["mappings"] as String
        isEnableRelocation = true
        relocationPrefix = "${project.group.toString().lowercase()}.${(project.extra["archives_base_name"] as String).lowercase()}"
        archiveClassifier = ""
        project.configurations.implementation.get().isCanBeResolved = true
        configurations = listOf(project.configurations.implementation.get())
        minimize()
    }
    build { dependsOn(shadowJar) }
}