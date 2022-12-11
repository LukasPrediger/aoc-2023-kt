package io.github.lukasprediger.aoc.days.day8

import io.github.lukasprediger.aoc.common.readInputRaw

fun main() {
    val input = readInputRaw(8).trim().lines()
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    val trees = Trees(input.map { it.map { char -> char.digitToInt() } })

    return (0 until trees.width).flatMap { x ->
        (0 until trees.height).map { y -> trees.treeVisible(x, y) }
    }.filter { it }.size
}

fun part2(input: List<String>): Int {
    val trees = Trees(input.map { it.map { char -> char.digitToInt() } })

    return (0 until trees.width).flatMap { x ->
        (0 until trees.height).map { y -> trees.scenicScore(x, y) }
    }.max()
}


class Trees(private val trees: List<List<Int>>) {
    operator fun get(x: Int, y: Int): Int {
        return try {
            trees[y][x]
        } catch (e: IndexOutOfBoundsException) {
            println("$x: $y, $trees")
            throw e
        }
    }

    val height = trees.size
    val width = trees.first().size

    fun treeVisible(x: Int, y: Int): Boolean {
        return calculateHeights(x,y).any { it.allSmallerThanFirst() }
    }

    fun scenicScore(x: Int, y: Int): Int {
        return calculateHeights(x,y).map { it.takeUntilLargerThanFirst() }.map { it.size }.reduce{ acc, curr -> acc * curr }
    }

    private fun calculateHeights(x: Int, y: Int) = listOf(
        (y downTo 0).map { x to it }.map { get(it.first, it.second) }, // Up
        (y until height).map { x to it }.map { get(it.first, it.second) }, // Down
        (x downTo 0).map { it to y }.map { get(it.first, it.second) }, // Left
        (x until width).map { it to y }.map { get(it.first, it.second) }, // Right
    )
}

fun List<Int>.takeUntilLargerThanFirst(): List<Int> {
    if (size == 1) return emptyList()
    val height = first()
    return drop(1).takeUntil { it < height }
}

fun List<Int>.allSmallerThanFirst(): Boolean {
    if (size == 1) return true
    val height = first()
    return drop(1).max() < height
}

fun <T> List<T>.takeUntil(predicate: (T) -> Boolean): List<T> {
    val list = ArrayList<T>()
    for (item in this) {
        list.add(item)
        if (!predicate(item))
            break
    }
    return list
}