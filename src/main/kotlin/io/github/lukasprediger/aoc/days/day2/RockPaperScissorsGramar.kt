package io.github.lukasprediger.aoc.days.day2

import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.parser.Parser
import io.github.lukasprediger.aoc.common.Tokens

object RockPaperScissorsParser : Grammar<List<Pair<Play, Play>>>() {
    private val round by Tokens.CHAR and skip(Tokens.WHITESPACE) and Tokens.CHAR map {
        (opponent, proponent) -> Play.fromChar(opponent) to Play.fromChar(proponent)
    }

    private val roundWithOutcome by Tokens.CHAR and Tokens.CHAR map {
        (opponent, outcome) -> Play.fromChar(opponent) to Outcome.fromChar(outcome)
    }

    private val rounds by separatedTerms(round, Tokens.WHITESPACE)
    val roundsWithOutcome by separatedTerms(roundWithOutcome, Tokens.WHITESPACE)

    override val tokens = (super.tokens + Tokens.baseTokens).distinct()

    override val rootParser: Parser<List<Pair<Play, Play>>> = rounds
}