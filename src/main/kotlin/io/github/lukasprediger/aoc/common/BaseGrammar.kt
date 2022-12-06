package io.github.lukasprediger.aoc.common

import com.github.h0tk3y.betterParse.combinators.use
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken

object Tokens {
    val BLANK_LINE = regexToken("(?:\\h*\\n){2,}")

    val charToken = regexToken("\\w")
    val stringToken = regexToken("\\w+")

    val CHAR = charToken use { text.first() }
    val STRING = stringToken use { text }

    private val DIGITS = regexToken("\\d*")
    val INTEGER = DIGITS use { text.toInt() }

    val MINUS = literalToken("-")
    val COMMA = literalToken(",")

    val OPENING_BRACKET = literalToken("[")
    val CLOSING_BRACKET = literalToken("]")

    val SPACE = regexToken("\\s")
    val WHITESPACE = regexToken("\\s+", ignore = true)

    val baseTokens = listOf(charToken, stringToken, DIGITS, MINUS, COMMA, OPENING_BRACKET, CLOSING_BRACKET, SPACE, WHITESPACE)

}