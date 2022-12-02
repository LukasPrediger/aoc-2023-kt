package io.github.lukasprediger.aoc.common.day2

import com.github.h0tk3y.betterParse.combinators.and
import com.github.h0tk3y.betterParse.combinators.map
import com.github.h0tk3y.betterParse.combinators.separatedTerms
import com.github.h0tk3y.betterParse.combinators.skip
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.lexer.regexToken
import com.github.h0tk3y.betterParse.parser.Parser

object RockPaperScissorsParser : Grammar<List<Pair<Play, Play>>>() {

    private val CHAR by regexToken("\\w")
    private val WHITESPACE by regexToken("\\s+", ignore = true)

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