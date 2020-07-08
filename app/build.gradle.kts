
plugins {
    id("com.android.application")

    kotlin("android")

    kotlin("android.extensions")

    kotlin("kapt")

    id("quality")
}

android {
    compileSdkVersion(Version.compileSdkVersion)

    defaultConfig {
        applicationId = "com.mn.geotweets"
        minSdkVersion(Version.minSdkVersion)
        targetSdkVersion(Version.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        val options = this as org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
        options.jvmTarget = "1.8"
    }
}
androidExtensions {
    isExperimental = true
}
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    implementation(Libs.kotlinSdk)
    implementation(Libs.coroutinesCore)
    implementation(Libs.coroutinesAndroid)
    implementation(Libs.coroutinesRx)
    implementation(Libs.coroutinesRxUtils)
    implementation(Libs.lifecycle)
    kapt(Libs.lifeCycleCompiler)
    implementation(Libs.appCompat)
    implementation(Libs.appCompatCore)
    implementation(Libs.coreKtx)
    implementation(Libs.constraintLayout)
    implementation(Libs.cardView)
    implementation(Libs.retrofit)
    implementation(Libs.retrofitConverter)
    implementation(Libs.recyclerView)
    implementation(Libs.annotations)
    implementation(Libs.navigationFragment)
    implementation(Libs.navigationComponent)
    implementation(Libs.multidex)
    implementation(Libs.materialComponents)
    api(Libs.dagger)
    kapt(Libs.daggerCompiler)
    implementation(Libs.okhttp)
    implementation(Libs.okhttpLogging)
    implementation(Libs.signPost)
    implementation(Libs.okhttpSignPost)

    debugImplementation(Libs.leakCanaryDebug)

    testImplementation(Libs.mockk)
    testImplementation(Libs.junitApi)
    testRuntimeOnly(Libs.junitRuntime)
    androidTestImplementation(Libs.androidTestRunner)
    androidTestImplementation(Libs.espresso)

    implementation(project(":domain"))
    implementation(project(":data"))
}