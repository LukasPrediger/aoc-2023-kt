package io.github.lukasprediger.aoc.days.day1

import io.github.lukasprediger.aoc.common.readInput

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Int {
    return getElfCalories().max()
}

fun part2(): Int {
    return getElfCalories().sorted().takeLast(3).sum()
}

private fun getElfCalories(): List<Int> {
    val input = readInput(1).iterator()

    return generateSequence {
        while (input.hasNext()) {
            var current = 0
            var next = input.next()

            while (next.isNotBlank()) {
                current += next.toInt()
                next = if (input.hasNext()) input.next() else ""
            }
            return@generateSequence current
        }
        return@generateSequence null
    }.toList()
}