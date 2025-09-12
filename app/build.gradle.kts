import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.coopersimpson.androidnews"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.coopersimpson.androidnews"
        minSdk = 34
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        signingConfig = signingConfigs.getByName("debug")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildFeatures {
            buildConfig = true
        }

        val props = Properties().apply {
            load(rootProject.file("local.properties").inputStream())
        }
        val apiKey = checkNotNull(props.getProperty("API_KEY")) {
            "API_KEY is missing from local.properties"
        }

        buildConfigField(
            "String",
            "API_KEY",
            "\"$apiKey\""
        )
    }

    buildTypes {
        debug {
            isDebuggable = true

            // Don't enable optimisation for debug (faster build times and better stacktraces)
            isMinifyEnabled = false
            isShrinkResources = false
        }
        release {
            isDebuggable = false

            // Use Google's R8 to optimise our release build
            isMinifyEnabled = true // Enable code-related optimisation
            isShrinkResources = true // Enable resource shinking (remove unused resources)
            proguardFiles(
                // Default rules from Android SDK, can also use "proguard-android.txt" instead
                getDefaultProguardFile("proguard-android-optimize.txt"),
                // Define extra rules on top of your project with this file
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions += "app"
    productFlavors {
        create("develop") {
            dimension = "app"
            versionNameSuffix = "-dev"
        }
        create("prod") {
            dimension = "app"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.material3)

    // Retrofit + Gson
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.gson)

    // OkHttp
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // viewmodel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // hilt and hilt-navigation-compose
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // room db
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // unit tests
    testImplementation(libs.junit)

    // Instrumented tests to run on Device / emulator
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // These dependencies only run in the debug version of the app
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}