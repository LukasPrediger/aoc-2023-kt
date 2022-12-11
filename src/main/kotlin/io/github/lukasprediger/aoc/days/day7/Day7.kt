package io.github.lukasprediger.aoc.days.day7

import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken
import com.github.h0tk3y.betterParse.parser.Parser
import io.github.lukasprediger.aoc.common.readInputRaw
import io.github.lukasprediger.aoc.common.parseString
import java.io.FileNotFoundException
import java.util.*

fun main() {
    val input = readInputRaw(7)
    println(part1(input))
    println(part2(input))
}

fun part1(input: String): Int {
   val root = execute(ShellParser.parseString(input))


    var remaining = 100000

    var sizes = root.directories.map { it.size }.sorted().filter { it in 1 until remaining }

    while (remaining > sizes.min()) {
        remaining -= sizes.last()
        sizes = sizes.filter { it < remaining }
    }

    return 100000 - remaining
}

fun part2(input: String): Int = 0

fun execute(commands: List<Command>): FsEntry.Directory {
    val root = FsEntry.Directory("/")
    var currentPath = ""

    commands.forEach {
        when(it) {
            is Command.Cd -> currentPath += "/${it.file}"
            Command.CdRoot -> currentPath = ""
            Command.CdUp -> currentPath = currentPath.split("/").dropLast(1).joinToString("/")
            is Command.Ls -> root.traverse(currentPath.split("/").filter(String::isNotBlank)).files.addAll(it.files)
        }
    }

    return root
}

sealed class FsEntry() {
    abstract val name: String
    abstract val size: Int

    override fun hashCode(): Int = Objects.hash(name, size)
    override fun equals(other: Any?): Boolean {
        return if(other is FsEntry) this.hashCode() == other.hashCode() else false
    }

    class File(override val name: String, override val size: Int): FsEntry()

    data class Directory(override val name: String, val files: MutableList<FsEntry>) : FsEntry() {
        constructor(name: String): this(name, mutableListOf())

        val directories: List<Directory>
            get() = listOf(this) + files.filterIsInstance<Directory>().flatMap { it.directories }

        fun traverse(path: List<String>): Directory {
            if (path.isEmpty()) return this
            val nextDir = path.first()
            return (files.find { it.name == nextDir } as? Directory)?.traverse(path.drop(1))
                ?: throw FileNotFoundException(nextDir)
        }

        override val size
            get() = files.sumOf{ it.size }
    }
}

sealed class Command {
    object CdRoot : Command()
    object CdUp : Command()
    data class Cd(val file: String) : Command()
    data class Ls(val files: List<FsEntry>) : Command()
}

object ShellParser : Grammar<List<Command>>() {
    val COMMAND by literalToken("$")
    val DIR_TOKEN by literalToken("dir")
    val LS_TOKEN by literalToken("ls")
    val CD_TOKEN by literalToken("cd")
    val CD_UP_TOKEN by literalToken("..")
    val ROOT by literalToken("/")

    val DIGITS by regexToken("\\d+")
    val INT by DIGITS use { text.toInt() }

    val NAME_TOKEN by regexToken("\\w+(\\.\\w+)?")
    val NAME by NAME_TOKEN use { text }


    val NEWLINE by literalToken("\n")
    val WHITESPACE by regexToken("\\s+", ignore = true)

    val DIR by skip(DIR_TOKEN) and NAME map { FsEntry.Directory(it) }
    val FILE by INT and NAME map { (size, name) -> FsEntry.File(name, size) }

    val FILE_ENTRY by DIR or FILE

    val LS by skip(COMMAND) and skip(LS_TOKEN) and skip(NEWLINE) and separatedTerms(FILE_ENTRY, NEWLINE) map { Command.Ls(it) }

    val CD by skip(COMMAND) and skip(CD_TOKEN) and (NAME_TOKEN or CD_UP_TOKEN or ROOT) use {
        when (text) {
            "/" -> Command.CdRoot
            ".." -> Command.CdUp
            else -> Command.Cd(text)
        }
    }

    val COMMANDS by separatedTerms(LS or CD, NEWLINE)

    override val rootParser: Parser<List<Command>> = COMMANDS
}