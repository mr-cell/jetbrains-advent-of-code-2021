import kotlin.math.absoluteValue

class Day07(input: List<String>) {
    private val crabs: Map<Int, Int> = parseInput(input)

    private fun parseInput(input: List<String>) =
        input.flatMap { it.split(",") }.map { it.toInt() }.groupingBy { it }.eachCount()

    fun solvePart1() = solve { it }

    fun solvePart2(): Int = solve { distance -> (distance * (distance + 1)) / 2 }

    private fun solve(fuelCost: (Int) -> Int) = crabs.keys.asRange().minOf { target ->
        crabs.map { (crab, crabCount) ->
            fuelCost((target - crab).absoluteValue) * crabCount
        }.sum()
    }

    private fun Set<Int>.asRange(): IntRange = this.minOf { it }..this.maxOf { it }
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    val day7Test = Day07(testInput)
    check(day7Test.solvePart1() == 37)
    check(day7Test.solvePart2() == 168)

    val input = readInput("Day07")
    val day07 = Day07(input)
    println(day07.solvePart1())
    println(day07.solvePart2())
}
