plugins{
    id("ktlint")
    id("detektCheck")
}
tasks {
    "check"{
        dependsOn(tasks["ktlint"])
        dependsOn(tasks["detektCheck"])
    }
}