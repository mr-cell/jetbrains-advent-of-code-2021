typealias PolymerChain = Map<String, Long>

class Day14(input: List<String>) {
    private val lastChar = input.first().last()
    private val startingChain = parseStartingChain(input)
    private val instructions = parseInstructions(input)

    private fun parseInstructions(input: List<String>): Map<String, String>  =
        input.drop(2).flatMap { it.split(" -> ") }.chunked(2).associate { Pair(it.first(), it.last()) }

    private fun parseStartingChain(input: List<String>): PolymerChain  =
        input.first().windowed(2).groupingBy { it }.eachCount().mapValues { it.value.toLong() }

    private fun PolymerChain.react(instructions: Map<String, String>): PolymerChain = buildMap {
        this@react.forEach { (pair, count) ->
            instructions[pair]?.also {
                plus("${pair.first()}$it", count)
                plus("$it${pair.last()}", count)
            } ?: plus("${pair.first()}${pair.last()}", count)
        }
    }

    private fun <T> MutableMap<T, Long>.plus(key: T, amount: Long) {
        this[key] = this.getOrDefault(key, 0L) + amount
    }

    private fun Map<String, Long>.byCharFrequency(): Map<Char, Long> =
        this
            .map { it.key.first() to it.value }
            .groupBy({it.first}, {it.second})
            .mapValues { it.value.sum() + if (it.key == lastChar) 1 else 0 }

    private fun solve(iterations: Int): Long =
        (0 until iterations)
            .fold(startingChain) { polymer, _ -> polymer.react(instructions)}
            .byCharFrequency()
            .values
            .sorted()
            .let { it.last() - it.first() }

    fun solvePart1(): Long  = solve(10)

    fun solvePart2(): Long = solve(40)
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    val day14Test = Day14(testInput)
    check(day14Test.solvePart1() == 1588L)
    check(day14Test.solvePart2() == 2188189693529L)

    val input = readInput("Day14")
    val day14 = Day14(input)
    println(day14.solvePart1())
    println(day14.solvePart2())
}