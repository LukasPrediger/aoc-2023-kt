package io.github.lukasprediger.aoc.days.day2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day2KtTest {

    @Test
    fun testDraw() {
        part1("A X") shouldBe 4
    }

    @Test
    fun testWin() {
        part1("A Y") shouldBe 8
    }
    @Test
    fun testLoss() {
        part1("B X") shouldBe 1
    }

    @Test
    fun testExample() {
        part1(listOf(
            "A Y",
            "B X",
            "C Z"
        ).joinToString("\n")) shouldBe 15
    }
}

infix fun Any?.shouldBe(expected: Any?) {
    assertEquals(expected, this)
}