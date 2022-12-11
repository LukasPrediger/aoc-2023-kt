package io.github.lukasprediger.aoc.days.day7

import com.github.h0tk3y.betterParse.lexer.TokenMatchesSequence
import com.github.h0tk3y.betterParse.parser.parse
import io.github.lukasprediger.aoc.days.day2.shouldBe
import io.github.lukasprediger.aoc.days.day5.parseString
import io.github.lukasprediger.aoc.days.day7.FsEntry.Directory
import io.github.lukasprediger.aoc.days.day7.FsEntry.File
import org.junit.jupiter.api.Test

class ShellParserTest {
    private fun tokenize(input: String): TokenMatchesSequence = ShellParser.tokenizer.tokenize(input)

    @Test
    fun `parse cd root`() {
        ShellParser.CD.parse(tokenize("$ cd /")) shouldBe Command.CdRoot
    }

    @Test
    fun `parse cd up`() {
        ShellParser.CD.parse(tokenize("$ cd ..")) shouldBe Command.CdUp
    }

    @Test
    fun `Parse cd dir`() {
        ShellParser.CD.parse(tokenize("$ cd a")) shouldBe Command.Cd("a")
    }

    @Test
    fun `Parse file`() {
        ShellParser.FILE.parse(tokenize("100 a")) shouldBe File("a", 100)
    }

    @Test
    fun `Parse dir`() {
        ShellParser.DIR.parse(tokenize("dir a")) shouldBe Directory("a")
    }

    @Test
    fun `Parse fileEntry`() {
        ShellParser.FILE_ENTRY.parse(tokenize("100 a")) shouldBe File("a", 100)
        ShellParser.FILE_ENTRY.parse(tokenize("dir a")) shouldBe Directory("a")
    }

    @Test
    fun `Test example`() {
        val input = ShellParserTest::class.java.getResource("/Day7TestInput.txt")!!.readText()

        val commands = ShellParser.parseString(input)

        commands shouldBe listOf(
            Command.CdRoot,
            Command.Ls(listOf(
                Directory("a"),
                File("b.txt", 14848514),
                File("c.dat",8504156),
                Directory("d")
            )),
            Command.Cd("a"),
            Command.Ls(listOf(
                Directory("e"),
                File("f", 29116),
                File("g", 2557),
                File("h.lst", 62596),
            )),
            Command.Cd("e"),
            Command.Ls(listOf(
                File("i", 584)
            )),
            Command.CdUp,
            Command.CdUp,
            Command.Cd("d"),
            Command.Ls(listOf(
                File("j", 4060174),
                File("d.log", 8033020),
                File("d.ext", 5626152),
                File("k", 7214296),
            ))
        )


        val expected = Directory("/", mutableListOf(
            Directory("a", mutableListOf(
                Directory("e", mutableListOf(File("i", 584))),
                File("f", 29116),
                File("g", 2557),
                File("h.lst", 62596),
            )),
            File("b.txt",14848514),
            File("c.dat",8504156),
            Directory("d", mutableListOf(
                File("j", 4060174),
                File("d.log", 8033020),
                File("d.ext", 5626152),
                File("k", 7214296),
            ))
        ))

        execute(ShellParser.parseString(input)) shouldBe expected
    }

    @Test
    fun `Test ls`() {
        val input = StringBuilder().apply {
            appendLine("$ ls")
            appendLine("dir a")
            appendLine("500 b")
        }.toString()

        ShellParser.LS.parse(tokenize(input)) shouldBe Command.Ls(listOf(Directory("a"), File("b", 500)))
    }
}