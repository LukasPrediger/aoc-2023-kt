package io.github.lukasprediger.aoc.days.day8

import io.github.lukasprediger.aoc.days.day2.shouldBe
import org.junit.jupiter.api.Test

class Day8Test {
    @Test
    fun TestExample() {
        part1(listOf(
            "30373",
            "25512",
            "65332",
            "33549",
            "35390",
        )) shouldBe 21
    }

    @Test
    fun TestExamplePart2() {
        part2(listOf(
            "30373",
            "25512",
            "65332",
            "33549",
            "35390",
        )) shouldBe 8
    }

    @Test
    fun TestScenicScore() {
        Trees(
            listOf(
                "30373",
                "25512",
                "65332",
                "33549",
                "35390",
            ).map { it.map { it.digitToInt() } }
        ).scenicScore(2,1) shouldBe 4
    }
}