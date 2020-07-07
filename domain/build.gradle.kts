plugins {
    `java-library`

    id("kotlin")

    id("quality")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.kotlinSdk)
    api(Libs.dagger)
    implementation(Libs.coroutinesCore)
    implementation(Libs.coroutinesAndroid)
    implementation(Libs.coreKtx)

    testImplementation(Libs.mockk)
    testImplementation(Libs.junitApi)
    testImplementation(Libs.coroutinesTest)
    testRuntimeOnly(Libs.junitRuntime)
    testRuntimeOnly(Libs.hamKresst)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}