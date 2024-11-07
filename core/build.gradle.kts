plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.brandonkitt.core"
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        testInstrumentationRunner = TestConfig.testInstrumentationRunner
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Config.kotlinCompiler
    }
    compileOptions {
        sourceCompatibility = Config.javaVersion
        targetCompatibility = Config.javaVersion
    }
    kotlinOptions {
        jvmTarget = Config.jvmTarget
    }
}

dependencies {
    // Hilt
    implementation(Dep.Hilt.android)
    kapt(Dep.Hilt.compiler)
    // Retrofit
    implementation(Dep.Retrofit.core)
    implementation(Dep.Retrofit.converterGson)
    // Kotlin
    implementation(Dep.Kotlin.core)
    implementation(Dep.Lifecycle.runtimeKtx)
    // Compose
    implementation(Dep.Compose.ui)
    implementation(Dep.Compose.graphics)
    implementation(Dep.Compose.material)
    implementation(Dep.Compose.materialIcons)
    implementation(Dep.Compose.materialIconsExt)
    implementation(Dep.Compose.activity)
    implementation(Dep.Compose.material3)
    implementation(Dep.Compose.preview)
    implementation(platform(Dep.Compose.bom))
    // Testing
    testImplementation(TestDep.junit)
    androidTestImplementation(TestDep.junitExt)
    androidTestImplementation(TestDep.espresso)
    androidTestImplementation(platform(Dep.Compose.bom))
    androidTestImplementation(TestDep.junit4)
    // Debug
    debugImplementation(Dep.Compose.tooling)
    debugImplementation(Dep.Compose.testManifest)
}