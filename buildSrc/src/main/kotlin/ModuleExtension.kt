object ModuleExtension {
    const val compileSdkVersion = 32
    const val jvmTarget = "11"

    object DefaultConfigs {
        const val minSdkVersion = 21
        const val targetSdkVersion = 32
        const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        const val defaultConsumerProguardFiles = "consumer-rules.pro"
        const val proGuardRules = "proguard-rules.pro"
        const val defaultProguardOptimizeFileName = "proguard-android-optimize.txt"
    }

    object App {
        const val applicationId = "com.himanshoe.sample"
    }

    object FilePath {
        const val gitHooks = "gradle/scripts/git-hooks.gradle.kts"
        const val detekt = "gradle/config/detekt.yml"
    }
}
