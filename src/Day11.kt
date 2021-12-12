class Day11(input: List<String>) {
    private val octopi = parseInput(input)

    private fun parseInput(input: List<String>): Array<IntArray> =
        input.map { row ->
                row.split("")
                    .filter { it.isNotBlank() }
                    .map { it.toInt() }
                    .toIntArray()
        }.toTypedArray()

    fun solvePart1(): Int {
        var flashesCounter = 0
        for (step in 1..100) {
            val willFlash = mutableListOf<Point2d>()
            val flashed = mutableSetOf<Point2d>()
            for (y in octopi.indices) {
                for (x in octopi[y].indices) {
                    val point = Point2d(x, y)
                    octopi[point]++
                    if (octopi[point] > 9) {
                        willFlash.add(point)
                        flashed.add(point)
                    }
                }
            }

            while (willFlash.isNotEmpty()) {
                willFlash.removeFirst().also { flashed.add(it) }.validNeighbours()
                    .forEach { point ->
                        octopi[point]++
                        if (octopi[point] > 9 && point !in flashed && point !in willFlash) {
                            willFlash.add(point)
                        }
                    }
            }

            flashed.forEach { point -> octopi[point] = 0 }
            flashesCounter += flashed.size

        }
        return flashesCounter
    }

    fun solvePart2(): Int {
        var step = 0
        while (true) {
            step++
            val willFlash = mutableListOf<Point2d>()
            val flashed = mutableSetOf<Point2d>()
            for (y in octopi.indices) {
                for (x in octopi[y].indices) {
                    val point = Point2d(x, y)
                    octopi[point]++
                    if (octopi[point] > 9) {
                        willFlash.add(point)
                        flashed.add(point)
                    }
                }
            }

            while (willFlash.isNotEmpty()) {
                willFlash.removeFirst().also { flashed.add(it) }.validNeighbours()
                    .forEach { point ->
                        octopi[point]++
                        if (octopi[point] > 9 && point !in flashed && point !in willFlash) {
                            willFlash.add(point)
                        }
                    }
            }

            flashed.forEach { point -> octopi[point] = 0 }

            if (flashed.size == 100) {
                return step
            }

        }
    }

    private operator fun Array<IntArray>.get(point: Point2d): Int = this[point.y][point.x]

    private operator fun Array<IntArray>.set(point: Point2d, value: Int) {
        this[point.y][point.x] = value
    }

    private operator fun Array<IntArray>.contains(point: Point2d): Boolean =
        point.y in this.indices && point.x in this[point.y].indices

    private fun Array<IntArray>.print() {
        for (row in this) {
            println(row.fold("") { acc, i -> acc + i.toString() })
        }
        println()
    }

    private fun Point2d.validNeighbours(): List<Point2d> = neighbours().filter { it in octopi }

    private data class Point2d(val x: Int, val y: Int) {
        fun neighbours(): List<Point2d> =
            listOf(
                Point2d(x - 1, y),
                Point2d(x + 1, y),
                Point2d(x, y - 1),
                Point2d(x, y + 1),
                Point2d(x - 1, y - 1),
                Point2d(x - 1, y + 1),
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