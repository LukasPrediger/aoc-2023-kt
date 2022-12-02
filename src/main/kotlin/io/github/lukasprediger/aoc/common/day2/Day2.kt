package io.github.lukasprediger.aoc.common.day2

import io.github.lukasprediger.aoc.common.readInput

fun main() {
    val input = readInput(2)

    println(part1(input))
}

fun part1(input: List<String>): Int {
    return input.map { s ->
        val (opponent, proponent) = s.split(' ').map { Play.fromChar(it.first()) }
        return@map proponent.against(opponent).points + proponent.points
    }.sum()
}

val matchups = listOf(
    Play.PAPER to Play.ROCK,
    Play.ROCK to Play.SCISSORS,
    Play.SCISSORS to Play.PAPER
)

enum class Play(val points: Int, vararg chars: Char) {
    ROCK(1, 'A', 'X'),
    PAPER(2, 'B', 'Y'),
    SCISSORS(3, 'C', 'Z');

    val chars = chars.toList()

    fun against(other: Play) = when (this) {
        ROCK -> when (other) {
            ROCK -> Outcome.DRAW
            PAPER -> Outcome.LOSS
            SCISSORS -> Outcome.WIN
        }

        PAPER -> when (other) {
            ROCK -> Outcome.WIN
            PAPER -> Outcome.DRAW
            SCISSORS -> Outcome.LOSS
        }

        SCISSORS -> when (other) {
            ROCK -> Outcome.LOSS
            PAPER -> Outcome.WIN
            SCISSORS -> Outcome.DRAW
        }
    }

    companion object {
        fun fromChar(char: Char) = values().find { it.chars.contains(char) }!!
    }
}

enum class Outcome(val points: Int) {
    WIN(6),
    DRAW(3),
    LOSS(0)
}

//Rock A X
//Paper B Y
// Scissors C Z