package io.github.lukasprediger.aoc.days.day5

import io.github.lukasprediger.aoc.days.day2.shouldBe
import org.junit.jupiter.api.Test

class OperationTest {
    @Test
    fun testSingleMove() {
        Operation(1, 0, 1).process(listOf(listOf('A'), emptyList())) shouldBe listOf(emptyList(), listOf('A'))
    }
}