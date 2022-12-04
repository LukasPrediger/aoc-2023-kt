package io.github.lukasprediger.aoc.common

import com.github.h0tk3y.betterParse.combinators.oneOrMore
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.lexer.regexToken

abstract class BaseGrammar<T>: Grammar<T>() {
    protected val BLANK_LINE by regexToken("(?:\\h*\\n){2,}")

    protected val CHAR by regexToken("\\w")
    protected val STRING by oneOrMore(CHAR)

    protected val WHITESPACE by regexToken("\\s+", ignore = true)
}