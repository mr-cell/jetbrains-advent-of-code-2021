import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int {
        val counter = IntArray(input[0].length)
        input
            .map { it.asSequence() }
            .forEach { it.forEachIndexed { index, c ->
                when (c) {
                    '0' -> counter[index]--
                    '1' -> counter[index]++
                }
            } }
        counter.reverse()
        val gamma = counter.foldIndexed(0) { index, agg, v ->
            return@foldIndexed if (v > 0) {
                agg + 1 * 2.0.pow(index.toDouble()).toInt()
            } else {
                agg
            }
        }

        val epsilon = counter.foldIndexed(0) { index, agg, v ->
            return@foldIndexed if (v < 0) {
                agg + 1 * 2.0.pow(index.toDouble()).toInt()
            } else {
                agg
            }
        }
        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {
        val size = input[0].length
        var entries = input
        IntRange(0, size - 1).forEach { i ->
            if (entries.size > 1) {
                var counter = 0
                entries
                    .map { it.asSequence() }
                    .forEach {
                        it.forEachIndexed { index, c ->
                            if (index == i) {
                                when (c) {
                                    '0' -> counter--
                                    '1' -> counter++
                                }
                            }
                        }
                    }
                entries = entries.filter { entry ->
                    if (counter >= 0) {
                        entry[i] == '1'
                    } else {
                        entry[i] == '0'
                    }
                }
            }
        }
        val o2Rating = entries[0].toInt(2)

        entries = input
        IntRange(0, size - 1).forEach { i ->
            if (entries.size > 1) {
                var counter = 0
                entries
                    .map { it.asSequence() }
                    .forEach {
                        it.forEachIndexed { index, c ->
                            if (index == i) {
                                when (c) {
                                    '0' -> counter--
                                    '1' -> counter++
                                }
                            }
                        }
                    }
                entries = entries.filter { entry ->
                    if (counter >= 0) {
                        entry[i] == '0'
                    } else {
                        entry[i] == '1'
                    }
                }
            }
        }
        val co2Rating = entries[0].toInt(2)
        println("$o2Rating, $co2Rating")
        return o2Rating * co2Rating
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
