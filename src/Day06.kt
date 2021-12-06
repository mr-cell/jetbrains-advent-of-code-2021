import java.util.*

fun main() {
    fun parseInput(input: List<String>) : List<Int> {
        return input.flatMap { it.split(",") }.map { it.toInt() }
    }

    fun runSimulation(input: List<Int>, days: Int): Int {
        val fishes = mutableListOf<Int>()
        fishes.addAll(input)
        for (day in 1..days) {
            val newFishes = mutableListOf<Int>()
            for (i in 0 until fishes.size) {
                if (fishes[i] == 0) {
                    newFishes.add(8)
                    fishes[i] = 6
                } else {
                    fishes[i]--
                }
            }
            fishes.addAll(newFishes)
        }
        return fishes.size
    }

    fun part1(input: List<String>): Int {
        val fishes = mutableListOf<Int>()
        fishes.addAll(parseInput(input))

        return runSimulation(fishes, 80)
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934)
    check(part2(testInput) == 0)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
