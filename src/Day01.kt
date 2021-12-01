fun main() {
    fun countDepthIncreases(input: List<String>): Int {
        return input.map { it.toInt() }.zipWithNext().count { it.first < it.second }
    }

    fun part2(input: List<String>): Int {
        return input.map { it.toInt() }.windowed(3).map { it.sum() }.zipWithNext().count { it.first < it.second }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(countDepthIncreases(testInput) == 7)

    val input = readInput("Day01")
    println(countDepthIncreases(input))
    println(part2(input))
}
