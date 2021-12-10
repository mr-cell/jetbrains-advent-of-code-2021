import java.util.*

class Day10(private val input: List<String>) {
    companion object {
        private val scoresPart1 = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
        private val scoresPart2 = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)
        private val openToClosed = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
    }

    fun solvePart1(): Long {
        return input
            .map { parseRow(it) }
            .filterIsInstance<Corrupted>()
            .sumOf { it.score().toLong() }
    }

    fun solvePart2(): Long {
        return input
            .map { parseRow(it) }
            .filterIsInstance<Incomplete>()
            .map { it.score() }
            .sorted()
            .midpoint()
    }

    private fun parseRow(row: String): ParseResult {
        val stack = ArrayDeque<Char>()
        row.forEach { symbol ->
            when {
                symbol in openToClosed -> stack.addLast(symbol)
                symbol != openToClosed[stack.removeLast()] -> return Corrupted(symbol)
            }
        }

        return if (stack.isEmpty()) Success else Incomplete(stack.reversed())
    }

    private sealed interface ParseResult
    private object Success: ParseResult
    private class Incomplete(val pending: List<Char>): ParseResult {
        fun score(): Long =
            pending
                .map { openToClosed.getValue(it) }
                .fold(0) { acc, symbol -> (acc * 5) + scoresPart2.getValue(symbol) }
    }
    private class Corrupted(val actual: Char): ParseResult {
        fun score(): Int = scoresPart1.getValue(actual)
    }
}

fun <T> List<T>.midpoint(): T = this[lastIndex/2]

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    val day10Test = Day10(testInput)
    check(day10Test.solvePart1() == 26397L)
    check(day10Test.solvePart2() == 288957L)

    val input = readInput("Day10")
    val day10 = Day10(input)
    println(day10.solvePart1())
    println(day10.solvePart2())
}