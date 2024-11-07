plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.brandonkitt.nexttogo"
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
    // Retrofit
    implementation(Dep.Retrofit.core)
    // Hilt
    implementation(Dep.Hilt.android)
    implementation(Dep.Hilt.navigation)
    kapt(Dep.Hilt.compiler)
    // Kotlin
    implementation(Dep.Kotlin.core)
    // Lifecycle
    implementation(Dep.Lifecycle.runtimeKtx)
    // Compose
    implementation(Dep.Compose.activity)
    implementation(Dep.Compose.ui)
    implementation(Dep.Compose.graphics)
    implementation(Dep.Compose.preview)
    implementation(Dep.Compose.material3)
    implementation(platform(Dep.Compose.bom))
    // Debug
    debugImplementation(Dep.Compose.tooling)
    debugImplementation(Dep.Compose.testManifest)
    // Unit Testing
    testImplementation(TestDep.junit)
    testImplementation(TestDep.androidXTest)
    testImplementation(TestDep.arch)
    testImplementation(TestDep.mockk)
    testImplementation(TestDep.truth)
    testImplementation(TestDep.coroutinesTest)
    // Instrumentation Testing
    androidTestImplementation(TestDep.mockkAndroid)
    androidTestImplementation(TestDep.mockkAgent)
    androidTestImplementation(TestDep.espresso)
    androidTestImplementation(platform(Dep.Compose.bom))
    androidTestImplementation(TestDep.junit4)
}