import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    `java-library`

    id("kotlin")

    id("quality")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.kotlinSdk)
    implementation(Libs.coroutinesCore)
    implementation(Libs.coroutinesAndroid)
    implementation(Libs.coroutinesRx)
    implementation(Libs.coroutinesRxUtils)
    implementation(Libs.retrofit)
    implementation(Libs.retrofitConverter)
    implementation(Libs.retrofitCallAdapter)
    api(Libs.dagger)

    implementation(project(":domain"))

    testImplementation(Libs.junitApi)
    testRuntimeOnly(Libs.junitRuntime)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
