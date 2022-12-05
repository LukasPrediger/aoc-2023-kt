package io.github.lukasprediger.aoc.common

import com.github.h0tk3y.betterParse.combinators.map
import com.github.h0tk3y.betterParse.combinators.oneOrMore
import com.github.h0tk3y.betterParse.combinators.use
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken
import com.github.h0tk3y.betterParse.parser.parse

@Suppress("PropertyName")
abstract class BaseGrammar<T> : Grammar<T>() {
    protected val BLANK_LINE by regexToken("(?:\\h*\\n){2,}")

    private val charToken by regexToken("\\w")

    protected val CHAR by charToken map { it.value.text.first() }
    protected val STRING by oneOrMore(CHAR) map { it.joinToString() }

    protected val DIGITS by regexToken("\\d*")
    protected val INTEGER by DIGITS use { text.toInt() }

    protected val MINUS by literalToken("-")
    protected val COMMA by literalToken(",")

    protected val OPENING_BRACKET by literalToken("[")
    protected val CLOSING_BRACKET by literalToken("]")

    protected val SPACE by regexToken("\\s")
    protected val WHITESPACE by regexToken("\\s+", ignore = true)
    
    fun parseString(input: String) = parse(tokenizer.tokenize(input))
}