import java.util.*

typealias ChitonCave = Array<IntArray>

class Day15(input: List<String>) {
    private val cave = parseInput(input)

    private fun parseInput(input: List<String>): ChitonCave  =
        input.map { row ->
            row.map { risk -> risk.digitToInt() }.toIntArray()
        }.toTypedArray()

    fun solvePart1(): Int = cave.traverse()

    fun solvePart2(): Int = cave.traverse(Point2d((cave.first().size * 5) - 1, (cave.size * 5) - 1))

    private fun ChitonCave.traverse(destination: Point2d = Point2d(first().lastIndex, lastIndex)): Int {
        val toBeEvaluated = PriorityQueue<Traversal>().apply { add(Traversal(Point2d(0, 0), 0)) }
        val visited = mutableSetOf<Point2d>()

        while (toBeEvaluated.isNotEmpty()) {
            val thisPlace = toBeEvaluated.poll()
            if (thisPlace.point == destination) {
                return thisPlace.totalRisk
            }

            if (thisPlace.point !in visited) {
                visited.add(thisPlace.point)
                thisPlace.point.neighbours()
                    .filter { it.x in (0..destination.x) && it.y in (0..destination.y) }
                    .forEach { toBeEvaluated.offer(Traversal(it, thisPlace.totalRisk + this[it])) }
            }
        }
        error("No paths to destination")
    }

    private operator fun ChitonCave.get(point: Point2d): Int {
        val dx = point.x / this.first().size
        val dy = point.y / this.size
        val originalRisk = this[point.y % this.size][point.x % this.first().size]
        val newRisk = (originalRisk + dx + dy)
        return newRisk.takeIf { it < 10 } ?: (newRisk - 9)
    }

    private class Traversal(val point: Point2d, val totalRisk: Int): Comparable<Traversal> {
        override fun compareTo(other: Traversal): Int = this.totalRisk - other.totalRisk
    }

    private data class Point2d(val x: Int, val y: Int) {
        fun neighbours(): List<Point2d> =
            listOf(
                Point2d(x - 1, y),
                Point2d(x + 1, y),
                Point2d(x, y - 1),
                Point2d(x, y + 1)
            )
    }
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    val day15Test = Day15(testInput)
    check(day15Test.solvePart1() == 40)
    check(day15Test.solvePart2() == 315)

    val input = readInput("Day15")
    val day15 = Day15(input)
    println(day15.solvePart1())
    println(day15.solvePart2())
}