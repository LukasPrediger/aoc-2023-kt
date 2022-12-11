package io.github.lukasprediger.aoc.days.day4

import com.github.h0tk3y.betterParse.parser.parseToEnd
import io.github.lukasprediger.aoc.days.day2.shouldBe
import io.github.lukasprediger.aoc.days.day5.parseString
import org.junit.jupiter.api.Test

class RangeGrammarTest{
    @Test
    fun `Test Range`() {
        RangeGrammar.range.parseToEnd(RangeGrammar.tokenizer.tokenize("1-2")) shouldBe 1..2
    }
    @Test
    fun `Test Range Pair`() {
        RangeGrammar.parseString("1-2,2-3") shouldBe (1..2 to 2..3)
    }


}