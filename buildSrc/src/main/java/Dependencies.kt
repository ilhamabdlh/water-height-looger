import BuildPlugins.Version.kotlin

object BuildPlugins {

    object Version {
        const val buildTools = "4.1.0-rc03"
        const val kotlin = "1.3.72"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Version.buildTools}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin"
    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
}

object Dependencies {
    val retrofit = "com.squareup.retrofit2:retrofit:2.6.0"
    val gsonConverter = "com.squareup.retrofit2:converter-gson:2.8.0"
    val mpAndroidChart = "com.github.PhilJay:MPAndroidChart:v3.1.0"
    val lottie = "com.airbnb.android:lottie:3.4.4"
}