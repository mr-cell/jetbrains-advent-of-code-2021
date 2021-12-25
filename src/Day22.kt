class Day22(input: List<String>) {
    private val instructions: List<Instruction> = input.map { Instruction.of(it) }
    private val cubes = mutableMapOf<Point3d, Boolean>()

    fun solvePart1(): Int {
        instructions
            .filter { it.inRange(-50..50, -50..50, -50..50) }
            .forEach { instruction ->
                instruction.points().forEach { point ->
                    if (cubes.contains(point) && instruction.off) {
                        cubes.remove(point)
                    } else if (instruction.on) {
                        cubes[point] = instruction.on
                    }
                }
            }
        return cubes.size
    }

    fun solvePart2(): Int = 0

    private data class Point3d(val x: Int, val y: Int, val z: Int)

    private class Instruction(val on: Boolean, val xRange: IntRange, val yRange: IntRange, val zRange: IntRange) {
        val off: Boolean = !on

        fun inRange(xRange: IntRange, yRange: IntRange, zRange: IntRange): Boolean =
            this.xRange.first >= xRange.first &&
                    this.xRange.last <= xRange.last &&
                    this.yRange.first >= yRange.first &&
                    this.yRange.last <= yRange.last &&
                    this.zRange.first >= zRange.first &&
                    this.zRange.last <= zRange.last

        fun points(): List<Point3d> {
            val points = mutableListOf<Point3d>()
            xRange.forEach { x ->
                yRange.forEach { y ->
                    zRange.forEach { z->
                        points.add(Point3d(x, y, z))
                    }
                }
            }
            return points
        }

        companion object {
            fun of(input: String): Instruction {
                val splitInput = input.split(" ")
                val isOn = when(splitInput.first()) {
                    "on" -> true
                    "off" -> false
                    else -> error("Invalid on/off flag")
                }
                val xRange = splitInput.last().getRange("x=", ",")
                val yRange = splitInput.last().getRange("y=", ",")
                val zRange = splitInput.last().getRange("z=")
                return Instruction(isOn, xRange, yRange, zRange)
            }

            private fun String.getRange(rangePrefix: String, rangeSuffix: String? = null): IntRange {
                val rangeStartIndex = this.indexOf(rangePrefix) + rangePrefix.length
                val rangeEndIndex = rangeSuffix?.let { this.indexOf(it, rangeStartIndex) } ?: this.length
                val range = this.substring(rangeStartIndex, rangeEndIndex).split("..").map { it.toInt() }
                return IntRange(range.first(), range.last())
            }
        }
    }
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day22_test")
    val day22Test = Day22(testInput)
    check(day22Test.solvePart1() == 474140)
    check(day22Test.solvePart2() == 0)

    val input = readInput("Day22")
    val day22 = Day22(input)
    println(day22.solvePart1())
    println(day22.solvePart2())
}