import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties

plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.google.service)
    alias(libs.plugins.firebase.crashlytics)
}

val keyStorePropertiesFile = rootProject.file("keystore.properties")
val keyStoreProperty = Properties()
keyStoreProperty.load(FileInputStream(keyStorePropertiesFile))

android {
    signingConfigs {
        create("release") {
            storeFile = file(keyStoreProperty["KEYSTORE_FILE"] as String)
            storePassword = keyStoreProperty["KEYSTORE_PASSWORD"] as String
            keyAlias = keyStoreProperty["KEY_ALIAS"] as String
            keyPassword = keyStoreProperty["KEY_PASSWORD"] as String
        }
    }
    namespace = "com.example.posapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.posapplication"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    // Always show the result of every unit test, even if it passes.
    testOptions.unitTests {
        isIncludeAndroidResources = true
        beforeEvaluate {
            println(">> Running unit test:")
        }
        all { test ->
            with(test) {
                filter {
                    excludeTestsMatching("com.example.note.database.*")
                }
                testLogging {
                    events =
                        setOf(
                            org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
                            org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
                            org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
                            org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT,
                            org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR,
                        )
                }
                afterSuite(
                    KotlinClosure2<TestDescriptor, TestResult, Unit>({ desc, result ->
                        if (desc.parent == null) { // Will match the outermost suite
                            val output =
                                """
                                Results: ${result.resultType} (${result.testCount} tests, ${result.successfulTestCount} passed, ${result.failedTestCount} failed, ${result.skippedTestCount} skipped)
                                """.trimIndent()

                            val startItem = "|  "
                            val endItem = "  |"
                            val repeatLength = startItem.length + output.length + endItem.length

                            println(
                                "\n" + "-".repeat(repeatLength) + "\n" + startItem +
                                    output + endItem + "\n" + "-".repeat(repeatLength),
                            )
                        }
                    }),
                )
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // App dependencies
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)

    // Android Core Library
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.test)
    androidTestImplementation(libs.androidx.test)
    androidTestImplementation(libs.androidx.junit.ktx)

    // Local unit tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.constraintlayout.compose)
    testImplementation(libs.google.truth)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.koin.androidx.test)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.junit.test)
    testImplementation(libs.mockk.lib)
    androidTestImplementation(libs.android.mockk)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.robolectric.test)
    testImplementation(libs.androidx.junit.ktx)

    // Room
    implementation(libs.room.runtime)
    ksp(libs.room.complier)
    implementation(libs.room.ktx)

    // koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android.compat)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.navigation)
    implementation(libs.koin.androidx.workmanager)
    implementation(libs.koin.androidx.test)

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.logging.interceptor)

    // glide
    implementation(libs.glide)

    // logging
    implementation(libs.timber)

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
}

// Ktlint configuration
ktlint {
    version.set("1.3.1")
    debug.set(true)
    android.set(true)
    ignoreFailures.set(false)
    outputToConsole.set(true)
}

// =========================GRADLE TASK======================
tasks.register("renameApks") {
    val buildDirPath =
        layout.buildDirectory.asFile
            .get()
            .absolutePath
    val outputDir = "$buildDirPath/outputs/apk"

    doLast {
        val date = SimpleDateFormat("yyyy-MM-dd").format(Date())

        android.applicationVariants.all { variant ->
            val variantName = variant.name
            val versionName = variant.versionName
            val apkFilesDir = file("$outputDir/$variantName")
            var isRenameSuccess = false
            apkFilesDir.listFiles()?.forEach { apkFile ->
                if (apkFile.extension == "apk") {
                    val newFileName = "$variantName-$date-$versionName.apk"
                    val newFile = File(apkFilesDir, newFileName)
                    println("Renaming APK: ${apkFile.name} to ${newFile.name}")
                    isRenameSuccess = apkFile.renameTo(newFile)
                }
            }
            isRenameSuccess
        }
    }
}

tasks.register("printVersionName") {
    println(android.defaultConfig.versionName)
}
