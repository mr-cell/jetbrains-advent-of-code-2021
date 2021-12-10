
fun main() {
    fun parseInput(input: List<String>) : Array<Array<Int>> {
        return input
            .map { it.split("") }
            .map {
                it.filter { it.isNotBlank() }.map { i -> i.toInt() }
            }.map { it.toTypedArray() }
            .toTypedArray()
    }

    fun part1(inputData: List<String>): Int {
        val input = parseInput(inputData)
        var riskLevel = 0
        for (i in input.indices) {
            for (j in input[i].indices) {
                val currentField = input[i][j]
                val neighbours = mutableListOf<Int>()
                if (i > 0) {
                    neighbours.add(input[i-1][j])
                }
                if (i < input.indices.last) {
                    neighbours.add(input[i+1][j])
                }
                if (j > 0) {
                    neighbours.add(input[i][j-1])
                }
                if (j < input[i].indices.last) {
                    neighbours.add(input[i][j+1])
                }

                val neighboursMin = neighbours.minOf { it }
                if (currentField < neighboursMin) {
                    riskLevel += currentField + 1
                }
            }
        }
        return riskLevel
    }

    fun part2(inputData: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

class ComparablePair(val first: Int, val second: Int): Comparable<ComparablePair> {
    override fun compareTo(other: ComparablePair): Int {
        val firstCompared = first.compareTo(other.first)
        val secondCompared = second.compareTo(other.second)
        return if (firstCompared != 0) {
            firstCompared;
        } else {
            secondCompared;
        }
    }
}
