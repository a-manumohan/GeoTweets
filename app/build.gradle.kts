plugins {
    id("com.android.application")

    kotlin("android")

    kotlin("android.extensions")

    kotlin("kapt")

    id("quality")

    id("kotlin-android")

    id("androidx.navigation.safeargs.kotlin")
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

            buildConfigField("String", "BASE_URL", "\"https://api.twitter.com\"")
            buildConfigField("String", "API_KEY", "\"VMB8bFComVulwOqCFNjQXqwtw\"")
            buildConfigField(
                "String",
                "API_SECRET",
                "\"o4cfGL9EjiFff6X6dtm3pO2AoUkjE6X4s6wfwXAukdOaqpdA0y\""
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"

            buildConfigField("String", "BASE_URL", "\"https://api.twitter.com\"")
            buildConfigField("String", "API_KEY", "\"VMB8bFComVulwOqCFNjQXqwtw\"")
            buildConfigField(
                "String",
                "API_SECRET",
                "\"o4cfGL9EjiFff6X6dtm3pO2AoUkjE6X4s6wfwXAukdOaqpdA0y\""
            )
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
    implementation(Libs.lifecycle)
    kapt(Libs.lifeCycleCompiler)
    implementation(Libs.appCompat)
    implementation(Libs.appCompatCore)
    implementation(Libs.coreKtx)
    implementation(Libs.constraintLayout)
    implementation(Libs.cardView)
    implementation(Libs.retrofit)
    implementation(Libs.retrofitConverter)
    implementation(Libs.retrofitScalarConverter)
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
    implementation(Libs.location)
    implementation(Libs.googleMaps)
    implementation(Libs.glide)
    kapt(Libs.glideCompiler)

    testImplementation(Libs.junitApi)
    testRuntimeOnly(Libs.junitRuntime)
    androidTestImplementation(Libs.androidTestRunner)

    implementation(project(":domain"))
    implementation(project(":data"))
}