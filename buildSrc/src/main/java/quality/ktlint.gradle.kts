
val ktlint by configurations.creating

dependencies {
    ktlint("com.github.shyiko:ktlint:0.29.0")
}

task("ktlint", JavaExec::class) {
    group = "verification"
    description = "Check Kotlin code style."
    classpath = ktlint
    main = "com.github.shyiko.ktlint.Main"
    args = listOf("src/**/*.kt")
    // to generate report in checkstyle format prepend following args:
    // "--reporter=plain", "--reporter=checkstyle,output=${buildDir}/ktlint.xml"
    // see https://github.com/shyiko/ktlint#usage for more
}

task("ktlintFormat", JavaExec::class) {
    group = "formatting"
    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    main = "com.github.shyiko.ktlint.Main"
    args = listOf("-F", "src/**/*.kt")
}

tasks {
    "check"{
        dependsOn(tasks["ktlint"])
    }
}