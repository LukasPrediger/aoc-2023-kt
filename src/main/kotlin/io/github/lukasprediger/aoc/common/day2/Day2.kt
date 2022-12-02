package io.github.lukasprediger.aoc.common.day2

import com.github.h0tk3y.betterParse.parser.parse
import io.github.lukasprediger.aoc.common.readInputRaw
import java.lang.IllegalArgumentException

fun main() {
    val input = readInputRaw(2)

    println(part1(input))
    println(part2(input))
}

fun part1(input: String): Int = RockPaperScissorsParser
        .parse(RockPaperScissorsParser.tokenizer.tokenize(input))
        .map(::pointsForRound)
        .sum()

fun part2(input: String): Int = RockPaperScissorsParser.roundsWithOutcome
        .parse(RockPaperScissorsParser.tokenizer.tokenize(input))
        .map {
            it.first to playForOutcome(it.first, it.second)
        }.map(::pointsForRound).sum()

fun playForOutcome(opponent: Play, outcome: Outcome): Play = when (outcome) {
    Outcome.DRAW -> opponent
    Outcome.WIN -> matchups.entries.find { it.value == opponent }!!.key
    Outcome.LOSS -> matchups[opponent]!!
}

fun outcomeForMatchup(matchup: Pair<Play, Play>): Outcome = matchup.let { (proponent, oponent) ->
    when {
        proponent == oponent -> Outcome.DRAW
        matchups[proponent] == oponent -> Outcome.WIN
        else -> Outcome.LOSS
    }
}

fun pointsForRound(round: Pair<Play, Play>): Int = outcomeForMatchup(round).points + round.second.points

val matchups = mapOf(
        Play.PAPER to Play.ROCK,
        Play.ROCK to Play.SCISSORS,
        Play.SCISSORS to Play.PAPER
)

enum class Play(val points: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    companion object {
        fun fromChar(char: Char) = when(char) {
            'A', 'X' -> ROCK
            'B', 'Y' -> PAPER
            'C', 'Z' -> SCISSORS
            else -> throw IllegalArgumentException("Unknown char $char")
        }
    }
}

enum class Outcome(val points: Int) {
    WIN(6),
    DRAW(3),
    LOSS(0);

    companion object {
        fun fromChar(char: Char) = when (char) {
            'X' -> LOSS
            'Y' -> DRAW
            'Z' -> WIN
            else -> throw IllegalArgumentException("Unknown char $char")
        }
    }
}

//Rock A X
//Paper B Y
// Scissors C Z