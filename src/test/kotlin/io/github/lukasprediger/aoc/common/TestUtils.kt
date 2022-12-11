package io.github.lukasprediger.aoc.common

import org.junit.jupiter.api.Assertions

infix fun Any?.shouldBe(expected: Any?) {
    Assertions.assertEquals(expected, this)
}