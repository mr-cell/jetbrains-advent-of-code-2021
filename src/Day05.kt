import kotlin.math.absoluteValue
import kotlin.math.sign

class Day5(input: List<String>) {
    private val lines = parseInput(input)

    private fun parseInput(input: List<String>)  =
        input
            .flatMap { it.split(" -> ") }
            .flatMap { it.split(",") }
            .asSequence()
            .filter { it.isNotBlank() }
            .map { it.toInt() }
            .chunked(2)
            .map { Point2d(it.first(), it.last()) }
            .chunked(2)
            .map { Pair(it.first(), it.last()) }
            .toList()

    fun solvePart1(): Int = solve { it.first sharesAxisWith it.second }

    fun solvePart2(): Int = solve { true }

    private fun solve(lineFilter: (Pair<Point2d, Point2d>) -> Boolean) =
        lines
            .filter(lineFilter)
            .flatMap { it.first lineTo it.second }
            .groupingBy { it }
            .eachCount()
            .count { it.value > 1 }

    data class Point2d(val x: Int, val y: Int) {
        infix fun lineTo(other: Point2d): List<Point2d> {
            val xDelta = (other.x - x).sign
            val yDelta = (other.y - y).sign

            val steps = maxOf((x - other.x).absoluteValue, (y - other.y).absoluteValue)

            return (1..steps).scan(this) { last, _ -> Point2d(last.x + xDelta, last.y + yDelta)}
        }

        infix fun sharesAxisWith(other: Point2d): Boolean = x == other.x || y == other.y
    }
}


fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val day5Test = Day5(testInput)
    check(day5Test.solvePart1() == 5)
    check(day5Test.solvePart2() == 12)

    val input = readInput("Day05")
    val day5 = Day5(input)
    println(day5.solvePart1())
    println(day5.solvePart2())
}