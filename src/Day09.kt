class Day09(input: List<String>) {
    private val caves: Array<IntArray> = parseInput(input)

    fun solvePart1(): Int =
        caves.findLowPoints()
            .sumOf { caves[it] + 1 }

    fun solvePart2(): Int =
        caves.findLowPoints()
            .map { getBasin(it).size }
            .sortedDescending()
            .take(3)
            .reduce { a, b -> a * b }

    private fun getBasin(point: Point2d): Set<Point2d> {
        val visited = mutableSetOf(point)
        val queue = mutableListOf(point)

        while (queue.isNotEmpty()) {
            val newNeighbours = queue.removeFirst()
                .validNeighbours()
                .filter { it !in visited }
                .filter { caves[it] != 9 }

            visited.addAll(newNeighbours)
            queue.addAll(newNeighbours)
        }
        return visited
    }

    private fun parseInput(input: List<String>): Array<IntArray> =
        input.map { row ->
            row.map { it.digitToInt() }.toIntArray()
        }.toTypedArray()

    private fun Array<IntArray>.findLowPoints(): List<Point2d> =
        flatMapIndexed { y, row ->
            row.mapIndexed {x, value ->
                Point2d(x, y).takeIf { point ->
                    point.validNeighbours().map { caves[it] }.all { value < it }
                }
            }.filterNotNull()
        }

    private operator fun Array<IntArray>.get(point: Point2d): Int = this[point.y][point.x]

    private operator fun Array<IntArray>.contains(point: Point2d): Boolean =
        point.y in this.indices && point.x in this[point.y].indices

    private fun Point2d.validNeighbours(): List<Point2d> =
        neighbours().filter { it in caves }
}

data class Point2d(val x: Int, val y: Int) {
    fun neighbours(): List<Point2d> =
        listOf(
            Point2d(x - 1, y),
            Point2d(x + 1, y),
            Point2d(x, y - 1),
            Point2d(x, y + 1)
        )
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    val day9test = Day09(testInput)
    check(day9test.solvePart1() == 15)
    check(day9test.solvePart2() == 1134)

    val input = readInput("Day09")
    val day9 = Day09(input)
    println(day9.solvePart1())
    println(day9.solvePart2())
}
