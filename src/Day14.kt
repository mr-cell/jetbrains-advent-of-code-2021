typealias Instructions = Map<String, String>
typealias PolymerChain = String

class Day14(input: List<String>) {
    private val startingChain: PolymerChain = input[0]
    private val instructions = parseInputForInstructions(input)

    private fun parseInputForInstructions(input: List<String>): Instructions  = input
        .drop(2)
        .map { it.split(" -> ") }
        .map { Pair(it.first(), it.last()) }
        .associate { Pair(it.first, listOf(it.first.first(), it.second.first(), it.first.last()).joinToString(separator = "")) }

    private fun PolymerChain.steps(): Sequence<String> = sequence {
        var polymerChain = startingChain

        while (true) {
            polymerChain = polymerChain.toCharArray().toList()
                .windowed(2, 1) { it.joinToString(separator = "") }
                .map { instructions.getOrDefault(it, it) }
                .reduce { acc, elem -> acc + elem.drop(1) }
            yield(polymerChain)
        }
    }

    fun solvePart1(): Int  {
        val counts = startingChain.steps().take(10).last().toCharArray().toList().groupingBy { it }.eachCount()
        val min = counts.minOf { it.value }
        val max = counts.maxOf { it.value }
        return max - min
    }

    fun solvePart2(): Long {
        val counts = startingChain.steps().take(40).last().toCharArray().toList().groupingBy { it }.eachCount()
        val min = counts.minOf { it.value.toLong() }
        val max = counts.maxOf { it.value.toLong() }
        return max - min
    }
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    val day14Test = Day14(testInput)
    check(day14Test.solvePart1() == 1588)
    check(day14Test.solvePart2() == 2188189693529L)

    val input = readInput("Day14")
    val day14 = Day14(input)
    println(day14.solvePart1())
    println(day14.solvePart2())
}