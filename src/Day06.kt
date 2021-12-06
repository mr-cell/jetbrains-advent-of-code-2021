import java.util.*
import java.util.stream.Collectors

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

    fun part2(input: List<String>): Long {
        val fishes = parseInput(input)
        var fishesMap = mutableMapOf<Int, Long>()

        fishes.forEach {
            if (!fishesMap.containsKey(it)) {
                fishesMap[it] = 1
            } else {
                fishesMap[it]?.plus(1)?.let { count -> fishesMap.put(it, count) }
            }
        }

        for (day in 1..256) {
            var newFishes = 0L
            val temp = mutableMapOf<Int, Long>()
            fishesMap.keys.forEach { key ->
                if (key == 0) {
                    newFishes += fishesMap[key]!!
                    if (!temp.containsKey(6)) {
                        temp[6] = 0
                    }
                    temp[6] = temp[6]!! + fishesMap[key]!!
                } else {
                    if (!temp.containsKey(key-1)) {
                        temp[key-1] = 0
                    }
                    temp[key-1] = temp[key-1]!! + fishesMap[key]!!
                }
            }
            if (newFishes != 0L) {
                temp[8] = newFishes
            }
            fishesMap = temp
        }
        return fishesMap.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934)
    check(part2(testInput) == 26984457539L)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
