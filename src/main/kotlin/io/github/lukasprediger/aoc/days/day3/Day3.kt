package io.github.lukasprediger.aoc.days.day3

import io.github.lukasprediger.aoc.common.readInput

fun main() {
    println(part1())
    println(part2())
}

fun part1() = readInput(3).sumOf(::calculatePriority)

fun part2() = readInput(3).asSequence().filter { it.isNotBlank() }.chunked(3).map(::getCharForGroup).map(Char::priority).sum()

fun getCharForGroup(group: List<String>): Char =
        group.first().also(::println).filter { group[1].contains(it) and group[2].contains(it) }.toCharArray().also(::println).distinct()
            .also { if(it.size > 1) throw IllegalArgumentException("Too many items found $it") }
            .first()

fun calculatePriority(input: String): Int {
    if (input.isEmpty()) return 0

    if (input.length % 2 != 0) throw IllegalArgumentException("Input length must be even")

    val compartment1 = input.take(input.length/2)
    val compartment2 = input.takeLast(input.length/2)

    return compartment1.filter { compartment2.contains(it) }
            .groupBy { it }
            .map { it.key }
            .also { if(it.size > 1) throw IllegalArgumentException("Too many items found") }
            .map { it.priority }
            .firstOrNull() ?: 0

}

val Char.priority: Int
    get() = if(isLowerCase()) uppercaseChar().code - 64 else lowercaseChar().code - 70

