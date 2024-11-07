plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.brandonkitt.app"
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = "com.brandonkitt.app"
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = TestConfig.testInstrumentationRunner
        vectorDrawables {
            useSupportLibrary = true
        }
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
    compileOptions {
        sourceCompatibility = Config.javaVersion
        targetCompatibility = Config.javaVersion
    }
    kotlinOptions {
        jvmTarget = Config.jvmTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Config.kotlinCompiler
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
}


dependencies {
    implementation(project(":core"))
    implementation(project(":feature:nexttogo"))
    // Hilt
    kapt(Dep.Hilt.compiler)
    implementation(Dep.Hilt.android)
    implementation(Dep.Hilt.navigation)
    // Kotlin
    implementation(Dep.Kotlin.core)
    implementation(Dep.Lifecycle.runtimeKtx)
    // Compose
    implementation(Dep.Compose.activity)
    implementation(Dep.Compose.ui)
    implementation(Dep.Compose.graphics)
    implementation(Dep.Compose.preview)
    implementation(Dep.Compose.material3)
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