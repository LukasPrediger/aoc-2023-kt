package io.github.lukasprediger.aoc.days.day2

import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.parser.Parser
import io.github.lukasprediger.aoc.common.BaseGrammar

object RockPaperScissorsParser : BaseGrammar<List<Pair<Play, Play>>>() {
    private val round by CHAR and skip(WHITESPACE) and CHAR map {
        (opponent, proponent) -> Play.fromChar(opponent.text.first()) to Play.fromChar(proponent.text.first())
    }

    private val roundWithOutcome by CHAR and CHAR map {
        (opponent, outcome) -> Play.fromChar(opponent.text.first()) to Outcome.fromChar(outcome.text.first())
    }

    private val rounds by separatedTerms(round, WHITESPACE)
    val roundsWithOutcome by separatedTerms(roundWithOutcome, WHITESPACE)


    override val rootParser: Parser<List<Pair<Play, Play>>> = rounds
}