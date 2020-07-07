object Version {
    const val compileSdkVersion = 29
    const val minSdkVersion = 21
    const val targetSdkVersion = 29

    const val kotlin = "1.3.72"
    const val coroutines = "1.3.7"
    const val lifecycle = "2.0.0"
    const val appCompat = "1.1.0"
    const val recyclerView = "1.1.0"
    const val constraintLayout = "1.1.3"
    const val cardView = "1.0.0"
    const val coreKtx = "1.2.0"
    const val androidTestRunner = "1.1.1"
    const val espresso = "3.1.1"
    const val leakCanary = "2.0"
    const val retrofit = "2.5.0"
    const val retrofitCallAdapter = "0.9.2"
    const val googleServices = "4.3.3"
    const val dexCount = "1.0.0"
    const val mockk = "1.9.1.kotlin12"
    const val junitAndroidPlugin = "1.3.2.0"
    const val junit = "5.3.2"
    const val hamKrest = "1.7.0.0"
    const val navigation = "2.3.0-rc01"
    const val multidex = "2.0.1"
    const val materialComponents = "1.2.0-alpha06"
    const val dagger = "2.24"
    const val okhttp = "4.2.1"
}

object Libs {
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}"
    const val kotlinSdk = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Version.kotlin}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"
    const val coroutinesRx =
        "org.jetbrains.kotlinx:kotlinx-coroutines-reactive:${Version.coroutines}"
    const val coroutinesRxUtils =
        "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:${Version.coroutines}"
    const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Version.lifecycle}"
    const val lifeCycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Version.lifecycle}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutines}"
    const val appCompat = "androidx.appcompat:appcompat:${Version.appCompat}"
    const val appCompatCore = "androidx.core:core:${Version.appCompat}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Version.recyclerView}"
    const val annotations = "androidx.annotation:annotation:${Version.appCompat}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"
    const val cardView = "androidx.cardview:cardview:${Version.cardView}"
    const val coreKtx = "androidx.core:core-ktx:${Version.coreKtx}"
    const val androidTestRunner = "androidx.test:runner:${Version.androidTestRunner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Version.espresso}"
    const val leakCanaryDebug = "com.squareup.leakcanary:leakcanary-android:${Version.leakCanary}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
    const val retrofitConverter = "com.squareup.retrofit2:converter-gson:${Version.retrofit}"
    const val retrofitCallAdapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Version.retrofitCallAdapter}"
    const val googleServices = "com.google.gms:google-services:${Version.googleServices}"
    const val dexCount = "com.getkeepsafe.dexcount:dexcount-gradle-plugin:${Version.dexCount}"
    const val mockk = "io.mockk:mockk:${Version.mockk}"
    const val junitAndroidPlugin =
        "de.mannodermaus.gradle.plugins:android-junit5:${Version.junitAndroidPlugin}"
    const val junitApi = "org.junit.jupiter:junit-jupiter-api:${Version.junit}"
    const val junitRuntime = "org.junit.jupiter:junit-jupiter-engine:${Version.junit}"
    const val hamKresst = "com.natpryce:hamkrest:${Version.hamKrest}"
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Version.navigation}"
    const val navigationComponent = "androidx.navigation:navigation-ui-ktx:${Version.navigation}"
    const val safeArgs =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Version.navigation}"
    const val multidex = "com.android.support:multidex:${Version.multidex}"
    const val materialComponents =
        "com.google.android.material:material:${Version.materialComponents}"
    const val dagger = "com.google.dagger:dagger:${Version.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Version.dagger}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Version.okhttp}"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Version.okhttp}"
}