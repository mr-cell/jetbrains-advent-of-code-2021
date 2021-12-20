class Day06(input: List<String>) {
    private val fishGenerations = parseInput(input)

    private fun parseInput(input: List<String>) =
        LongArray(9).apply {
            input.first().split(",").map { it.toInt() }.forEach { this[it] += 1L }
        }

    fun solvePart1(): Long = simulateDays(80)

    fun solvePart2(): Long = simulateDays(256)

    private fun simulateDays(days: Int): Long {
        repeat(days) {
            fishGenerations.rotateLeftInPlace()
            fishGenerations[6] += fishGenerations[8]
        }
        return fishGenerations.sum()
    }

    private fun LongArray.rotateLeftInPlace() {
        val mostLeft = first()
        this.copyInto(this, startIndex = 1)
        this[this.lastIndex] = mostLeft
    }
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(Day06(testInput).solvePart1() == 5934L)
    check(Day06(testInput).solvePart2() == 26984457539L)

    val input = readInput("Day06")
    println(Day06(input).solvePart1())
    println(Day06(input).solvePart2())
}
