import java.util.*
import java.util.stream.Collectors
import kotlin.math.abs
import kotlin.math.roundToInt

fun main() {
    fun parseInput(input: List<String>) : List<Int> {
        return input.flatMap { it.split(",") }.map { it.toInt() }
    }


    fun part1(input: List<String>): Int {
        val positions = parseInput(input)
        val max = positions.maxOf { it }
        val min = positions.minOf { it }

        var minFuel = Int.MAX_VALUE
        for (i in min..max) {
            val fuel = positions.fold(0) { acc, pos -> acc + abs(pos - i) }
            if (fuel < minFuel) {
                minFuel = fuel
            }
        }
        return minFuel
    }

    fun sumOfSeries(seriesMaxElement: Int): Int = IntRange(0, seriesMaxElement).sum()

    fun part2(input: List<String>): Int {
        val positions = parseInput(input)
        val max = positions.maxOf { it }
        val min = positions.minOf { it }

        var minFuel = Int.MAX_VALUE
        for (i in min..max) {
            val fuel = positions.fold(0) { acc, pos -> acc + sumOfSeries(abs(pos - i)) }
            if (fuel < minFuel) {
                minFuel = fuel
            }
        }
        return minFuel
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
