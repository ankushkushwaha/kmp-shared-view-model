import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    sourceSets {
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }

        androidMain.dependencies {
            implementation("io.insert-koin:koin-android:3.2.0")
        }

        commonMain.dependencies {
            api("com.rickclephas.kmp:kmp-observableviewmodel-core:1.0.0-BETA-7")

            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            implementation("io.insert-koin:koin-core:3.2.0")
        }

        commonTest.dependencies {
            implementation("io.insert-koin:koin-test:3.2.0")
        }
    }
}

android {
    namespace = "com.solera.poc.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
