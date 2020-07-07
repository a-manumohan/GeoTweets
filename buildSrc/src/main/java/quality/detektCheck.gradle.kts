val detektConf by configurations.creating

dependencies {
    detektConf("io.gitlab.arturbosch.detekt:detekt-cli:1.1.1")
}

task("detektCheck", JavaExec::class) {
    group = "verification"
    description = "Check Kotlin code style."
    main = "io.gitlab.arturbosch.detekt.cli.Main"
    classpath = detektConf
    val input = "$projectDir"
    val config = "$rootDir/buildSrc/src/main/java/quality/detekt.yml"
    val filters = "**/build/**,**/test/**,**/resources/**,**.gradle.kts"
    val report = "html:$buildDir/reports/detekt.html"
    val params = listOf("-i", input,
        "-c", config,
        "--excludes", filters,
        "-r", report)
    args = params
}