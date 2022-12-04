package io.github.lukasprediger.aoc.days.day3

import io.github.lukasprediger.aoc.days.day2.shouldBe
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day3Test {
    @Test
    fun `an empty string returns 0`() {
        calculatePriority("") shouldBe 0
    }
    @Test
    fun `test character priorities`() {
        'a'.priority shouldBe 1
        'z'.priority shouldBe 26
        'A'.priority shouldBe 27
        'Z'.priority shouldBe 52
    }

    @Test
    fun `an uneven string throws an exception`() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            calculatePriority("A")
        }
    }

    @Test
    fun `a string with different characters returns 0`() {
        calculatePriority("AB") shouldBe 0
    }

    @Test
    fun `a string with different characters returns the character value`() {
        calculatePriority("aa") shouldBe 1
    }

    @Test
    fun `test example`() {
        calculatePriority("vJrwpWtwJgWrhcsFMMfFFhFp") shouldBe 16
    }
}