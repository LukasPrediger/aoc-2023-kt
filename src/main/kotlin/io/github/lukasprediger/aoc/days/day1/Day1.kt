package io.github.lukasprediger.aoc.days.day1

import io.github.lukasprediger.aoc.common.readInput

fun main() {
    val input = readInput(1)
    part1(input)
    part2(input)
}

fun part1(input: List<String>) {
    println(
        input.filter(String::isNotBlank).sumOf { line ->
            return@sumOf line.filter(Char::isDigit).let {
                (it.take(1) + it.takeLast(1)).toInt()
            }.also {
//                println("$line -> $it")
            }
        }
    )
}

fun part2(input: List<String>) {
    input.map { it.lowercase() }.map {
        it
            .replace("zero", "0")
            .replace("one", "1")
            .replace("two", "2")
            .replace("three", "3")
            .replace("four", "4")
            .replace("five", "5")
            .replace("six", "6")
            .replace("seven", "7")
            .replace("eight", "8")
            .replace("nine", "9")
    }.also(::part1)
}
