package io.github.lukasprediger.aoc.common

import java.io.File

private val homeFolder: String = System.getProperty("user.home")
private val aocFolder = File("$homeFolder/aoc2023").apply { mkdir() }

private fun getInputFile(day: Int) = File(aocFolder, "day$day.txt")
fun getInputFromRemote(day: Int) {
    val absolutePath = getInputFile(day).absolutePath

    val process = ProcessBuilder()
        .command("aoc download -y 2023 -d $day -I -i $absolutePath".split(' '))
        .inheritIO()
        .start()

    process.waitFor()
    if (process.exitValue() != 0) {
        throw IllegalStateException("aoc-cli returned unexpected exitValue ${process.exitValue()}")
    }
    println("Wrote input for day $day to $absolutePath")
}

fun readInputRaw(day: Int): String {
    val inputFile = getInputFile(day)
    if (!inputFile.exists()) {
        println("Input file ${inputFile.name} does not exist. Downloading")
        getInputFromRemote(day)
    }
    return inputFile.readText()
}

fun readInput(day: Int): List<String> = readInputRaw(day).lines()

fun readCsv(day: Int): List<List<String>> = readInput(day).map { it.split(",") }