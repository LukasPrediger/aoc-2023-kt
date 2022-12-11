package io.github.lukasprediger.aoc.days.day4

import com.github.h0tk3y.betterParse.combinators.and
import com.github.h0tk3y.betterParse.combinators.map
import com.github.h0tk3y.betterParse.combinators.skip
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.parser.Parser
import io.github.lukasprediger.aoc.common.Tokens.COMMA
import io.github.lukasprediger.aoc.common.Tokens.INTEGER
import io.github.lukasprediger.aoc.common.Tokens.MINUS
import io.github.lukasprediger.aoc.common.Tokens.baseTokens
import io.github.lukasprediger.aoc.common.readInput
import io.github.lukasprediger.aoc.common.parseString

fun main() {
    val input = readInput(4).filter(String::isNotBlank)

    println(part1(input))
    println(part2(input))
}



fun part1(input: List<String>): Int = input
        .map(RangeGrammar::parseString)
        .count { it.first.contains(it.second) or it.second.contains(it.first) }

fun part2(input: List<String>): Int = input
        .map(RangeGrammar::parseString)
        .count { it.first.overlaps(it.second) }


fun IntRange.contains(other: IntRange) = other.all { this.contains(it) }
fun IntRange.overlaps(other: IntRange) = other.any { this.contains(it) }

object RangeGrammar : Grammar<Pair<IntRange, IntRange>>() {
    val range by INTEGER and skip(MINUS) and INTEGER map { (first, second) -> first..second }

    private val rangePairs by range and skip(COMMA) and range map { (first, second) -> first to second }

    override val tokens = (super.tokens + baseTokens).distinct()

    override val rootParser: Parser<Pair<IntRange, IntRange>> = rangePairs
}
