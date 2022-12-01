package io.github.lukasprediger.aoc.common.day1

import io.github.lukasprediger.aoc.common.readInput

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Int {
    val elves = getElfCalories()

    return elves.max()
}

fun part2(): Int {
    val elves = getElfCalories()
    return elves.sorted().takeLast(3).sum()
}

private fun getElfCalories(): MutableList<Int> {
    val elves = mutableListOf<Int>()

    val input = readInput(1)

    var current = 0
    input.forEach {
        if (it.isBlank()) {
            elves.add(current)
            current = 0
        } else {
            current += it.toInt()
        }
    }
    return elves
}