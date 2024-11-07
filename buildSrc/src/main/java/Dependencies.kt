import org.gradle.api.JavaVersion

object Versions {
    const val kotlin = "1.9.0"
    const val kotlinCore = "1.13.1"
    const val compose = "1.5.1"
    const val composeMaterial3 = "1.3.1"
    const val composeBom = "2023.08.00"
    const val navigation = "2.5.3"
    const val lifecycle = "2.6.1"
    const val retrofit = "2.9.0"
    const val hilt = "2.48"
    const val hiltNavigationCompose = "1.2.0"
    const val androidGradlePlugin = "8.2.2"
}

object TestVersions {
    const val junit = "4.13.2"
    const val junitExt = "1.2.1"
    const val junit4 = "1.7.5"
    const val androidXTest = "1.6.1"
    const val arch = "2.1.0"
    const val truth = "1.1.5"
    const val mockk = "1.13.3"
    const val coroutinesTest = "1.7.3"
    const val espresso = "3.4.0"
}

object Config {
    const val kotlinCompiler = "1.5.1"
    const val jvmTarget = "1.8"
    const val compileSdk = 34
    const val minSdk = 24
    const val targetSdk = 34
    val javaVersion = JavaVersion.VERSION_1_8
}

object TestConfig {
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object Dep {
    object Kotlin {
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val core = "androidx.core:core-ktx:${Versions.kotlinCore}"
    }

    object Navigation {
        const val composeNavigation =
            "androidx.navigation:navigation-compose:${Versions.navigation}"
    }

    object Compose {
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val preview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
        const val graphics = "androidx.compose.ui:ui-graphics:${Versions.compose}" // none
        const val material3 =
            "androidx.compose.material3:material3:${Versions.composeMaterial3}"
        const val material = "androidx.compose.material:material:${Versions.compose}" // none
        const val materialIcons =
            "androidx.compose.material:material-icons-core:${Versions.compose}" // none
        const val materialIconsExt =
            "androidx.compose.material:material-icons-extended:${Versions.compose}" // none
        const val activity =
            "androidx.activity:activity-compose:${Versions.compose}" // 1.9.0
        const val bom = "androidx.compose:compose-bom:${Versions.composeBom}"
        const val testManifest =
            "androidx.compose.ui:ui-test-manifest:${Versions.compose}" // none
        const val tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}" // none
    }

    object Hilt {
        const val android = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val compiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
        const val navigation =
            "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}" // 1.2.0
    }

    object Lifecycle {
        const val runtimeKtx =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}" // 2.6.1
    }

    object Retrofit {
        const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}" // 2.9.0
        const val converterGson =
            "com.squareup.retrofit2:converter-gson:${Versions.retrofit}" // 2.9.0
    }
}

object TestDep {
    const val junit = "junit:junit:${TestVersions.junit}" // 4.13.2
    const val junitExt = "androidx.test.ext:junit:${TestVersions.junitExt}" //1.2.1
    const val androidXTest = "androidx.test:core-ktx:${TestVersions.androidXTest}" // 1.6.1
    const val arch = "androidx.arch.core:core-testing:${TestVersions.arch}" // 2.1.0
    const val truth = "com.google.truth:truth:${TestVersions.truth}" // 1.1.5
    const val mockk = "io.mockk:mockk:${TestVersions.mockk}" // 1.13.3
    const val mockkAndroid = "io.mockk:mockk-android:${TestVersions.mockk}" // 1.13.3
    const val mockkAgent = "io.mockk:mockk-agent:${TestVersions.mockk}" // 1.13.3
    const val junit4 = "androidx.compose.ui:ui-test-junit4:${TestVersions.junit4}" // none
    const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${TestVersions.coroutinesTest}" // 1.7.3
    const val espresso = "androidx.test.espresso:espresso-core:${TestVersions.espresso}" // 3.4.0
}