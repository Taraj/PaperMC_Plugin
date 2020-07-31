plugins {
    java
    kotlin("jvm").version("1.3.72")
    id("com.github.johnrengelman.shadow").version("5.2.0")
}
group = "tk.tarajki"
version = "1.0-SNAPSHOT"
repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}
dependencies {
    implementation(kotlin("stdlib", "1.3.72"))
    //https://papermc.io/javadocs/paper/1.16/overview-summary.html
    compileOnly("com.destroystokyo.paper", "paper-api", "1.16.1-R0.1-SNAPSHOT")
}
tasks {
    shadowJar {
        archiveClassifier.set("")
        project.configurations.implementation.get().isCanBeResolved = true
        configurations = listOf(project.configurations.implementation.get())
        relocate("kotlin", "$group.src.main.kotlin")
    }
    build {
        dependsOn(shadowJar)
    }
}