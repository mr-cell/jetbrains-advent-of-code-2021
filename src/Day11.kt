typealias OctopusCave = Map<Day11.Point2d, Int>

class Day11(input: List<String>) {
    private val octopusCave = parseInput(input)

    private fun parseInput(input: List<String>): OctopusCave =
        input.flatMapIndexed { y, row ->
            row.mapIndexed { x, energy -> Point2d(x, y) to energy.digitToInt() }
        }.toMap()

    fun solvePart1(): Int = octopusCave.steps().take(100).sum()

    fun solvePart2(): Int = octopusCave.steps().indexOfFirst { it == octopusCave.size } + 1

    private fun OctopusCave.steps(): Sequence<Int> = sequence {
        val cave = this@steps.toMutableMap()

        while (true) {
            cave.forEach { (point, energy) -> cave[point] = energy + 1 }
            do {
                val flashesThisRound = cave.filterValues { it > 9 }.keys
                flashesThisRound.forEach { cave[it]  = 0 }

                flashesThisRound
                    .flatMap { it.neighbours() }
                    .filter { it in cave && cave[it] != 0 }
                    .forEach{ cave[it] = cave.getValue(it) + 1 }
            } while (flashesThisRound.isNotEmpty())

            yield(cave.count { it.value == 0 })
        }
    }

    data class Point2d(val x: Int, val y: Int) {
        fun neighbours(): List<Point2d> =
            listOf(
                Point2d(x - 1, y),
                Point2d(x + 1, y),
                Point2d(x, y - 1),
                Point2d(x, y + 1),
                Point2d(x - 1, y - 1),
                Point2d(x - 1 , y + 1),
                Point2d(x + 1, y - 1),
                Point2d(x + 1, y + 1)
            )
    }
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(Day11(testInput).solvePart1() == 1656)
    check(Day11(testInput).solvePart2() == 195)

    val input = readInput("Day11")
    println(Day11(input).solvePart1())
    println(Day11(input).solvePart2())
}