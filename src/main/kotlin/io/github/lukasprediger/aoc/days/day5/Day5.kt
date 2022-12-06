package io.github.lukasprediger.aoc.days.day5

import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken
import com.github.h0tk3y.betterParse.parser.Parser
import com.github.h0tk3y.betterParse.parser.parse
import io.github.lukasprediger.aoc.common.Tokens
import io.github.lukasprediger.aoc.common.readInputRaw
import javax.sql.RowSetEvent

fun main() {
    val input = readInputRaw(5)
    println(part1(input))
    println(part2(input))
}

fun part1(input: String): String = process(input, true)
fun part2(input: String): String = process(input, false)

fun process(input: String, reverseOrder: Boolean): String {
    val (cratesString, operationsString) = input.split("\n\n")
    var crates = cratesString.lines()
        .dropLast(1) // dropping numbers
        .map { line ->
            line.chunked(4)
                .map { if(it.length == 4) it.dropLast(1) else it }
                .map { if (it == "   ") null else it[1] }
        }.let {
            List(it.first().size) { index ->
                it.mapNotNull { row -> row[index] }
            }
        }.map(List<Char>::reversed)

    val operations = OperationGrammar.parseString(operationsString)

    operations.forEach {
        crates = it.process(crates, reverseOrder)
    }

    return crates.joinToString("") { it.last().toString() }
}


data class Operation(val amount: Int, val from: Int, val to: Int) {
    fun process(crates: List<List<Char>>, reverseOrder:Boolean = true): List<List<Char>> =
        crates.toMutableList().also {
            it[to] = it[to] + it[from].takeLast(amount).let { if(reverseOrder) it.reversed() else it }
            it[from] = it[from].dropLast(amount)
        }
}

object OperationGrammar : Grammar<List<Operation>>() {
    val moveToken by literalToken("move")
    val fromToken by literalToken("from")
    val toToken by literalToken("to")

    var operation: Parser<Operation> =
        skip(moveToken) and Tokens.INTEGER and skip(fromToken) and Tokens.INTEGER and skip(toToken) and Tokens.INTEGER map { (amount, from, to) ->
            Operation(amount, from - 1, to - 1)
        }

    val operations by separatedTerms(operation, Tokens.WHITESPACE)

    override val tokens = (super.tokens + Tokens.baseTokens).distinct()
    override val rootParser = operations

}

fun <T> Grammar<T>.parseString(input: String) = parse(tokenizer.tokenize(input))