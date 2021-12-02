fun main() {
    fun calculatePosition(input: List<String>): Int {
        return input
            .map { it.split(" ") }
            .fold(Pair(0, 0)) { coords, directions ->
                val value = directions[1].toInt()
                when (directions[0]) {
                    "forward" -> return@fold Pair(coords.first + value, coords.second)
                    "down" -> return@fold Pair(coords.first, coords.second + value)
                    "up" -> return@fold Pair(coords.first, coords.second - value)
                    else -> return@fold coords
                }
            }.let { it.first * it.second }
    }

    fun calculatePositionWithAim(input: List<String>): Int {
        return input
            .map { it.split(" ") }
            .fold(Triple(0, 0, 0)) { coords, directions ->
                val value = directions[1].toInt()
                when (directions[0]) {
                    "forward" -> return@fold Triple(
                        coords.first + value,
                        coords.second + (coords.third * value),
                        coords.third)
                    "down" -> return@fold Triple(
                        coords.first,
                        coords.second,
                        coords.third + value
                    )
                    "up" -> return@fold Triple(
                        coords.first,
                        coords.second,
                        coords.third - value
                    )
                    else -> return@fold coords
                }
            }.let { it.first * it.second }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(calculatePosition(testInput) == 150)
    check(calculatePositionWithAim(testInput) == 900)

    val input = readInput("Day02")
    println(calculatePosition(input))
    println(calculatePositionWithAim(input))
}
