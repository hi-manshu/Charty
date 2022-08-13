buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")

    }
    dependencies {
        classpath(Deps.Gradle.androidGradlePlugin)
        classpath(Deps.Gradle.kotlinGradlePlugin)
        classpath(Deps.Gradle.vanniktechGradlePlugin)
        classpath(Deps.Gradle.detektGradlePlugin)
        classpath(Deps.Gradle.ktlintGradlePlugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
subprojects {
    apply(plugin = Plugins.ktlint)
    apply(plugin = Plugins.detekt)

//    detekt {
//        config = files("$rootDir/${ModuleExtension.FilePath.detekt}")
//        buildUponDefaultConfig = true
//    }
}
allprojects {
    pluginManager.withPlugin(Plugins.vanniktechPublish) {
        extensions.getByType(com.vanniktech.maven.publish.MavenPublishPluginExtension::class.java)
            .apply {
                sonatypeHost = com.vanniktech.maven.publish.SonatypeHost.S01
            }
    }
}
