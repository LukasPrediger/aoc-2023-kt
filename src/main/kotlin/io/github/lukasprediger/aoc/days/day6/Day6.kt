package io.github.lukasprediger.aoc.days.day6

import io.github.lukasprediger.aoc.common.readInputRaw

class RingBuffer<T>(
    private val capacity: Int,
    private val buffer: MutableList<T> = mutableListOf()
) : MutableList<T> by buffer {
    override fun add(element: T): Boolean =
        buffer.add(element).also {
            if (buffer.size > capacity) buffer.removeFirst()

        }

    override fun toString(): String = buffer.toString()

    fun containsDuplicates(): Boolean = size != distinct().size
}

fun main() {
    val input = readInputRaw(6)
    println(part1(input))
    println(part2(input))
}

fun part1(input: String): Int = findStartMarker(input, 4)
fun part2(input: String): Int = findStartMarker(input, 14)

fun findStartMarker(input: String, size: Int): Int {
    val inputIter = input.iterator().withIndex()

    val buffer = RingBuffer<Char>(size)
    repeat(size) { buffer.add(inputIter.next().value) }

    while (buffer.containsDuplicates()) {
        buffer.add(inputIter.next().value)
    }

    return inputIter.next().index
}