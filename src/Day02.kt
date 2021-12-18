class Day02(private val input: List<String>) {
    fun solvePart1(): Int = moveSubmarine { submarine, command ->
        with(command) {
            when (direction) {
                "forward" -> submarine.copy(horizontal = submarine.horizontal + value)
                "up" -> submarine.copy(depth = submarine.depth - value)
                "down" -> submarine.copy(depth = submarine.depth + value)
                else -> submarine
            }
        }
    }.finalPosition()

    fun solvePart2(): Int = moveSubmarine { submarine, command ->
        with(command) {
            when(direction) {
                "forward" -> submarine.copy(horizontal =  submarine.horizontal + value, depth = submarine.depth + (submarine.aim * value))
                "up" -> submarine.copy(aim = submarine.aim - value)
                "down" -> submarine.copy(aim = submarine.aim + value)
                else -> submarine
            }
        }
    }.finalPosition()

    private fun moveSubmarine(moveFun: (Submarine, Command) -> Submarine): Submarine {
        return input.map { Command.of(it) }.fold(Submarine(), moveFun)
    }

    private data class Submarine(val horizontal: Int = 0, val depth: Int = 0, val aim: Int = 0) {
        fun finalPosition() = horizontal * depth
    }

    private class Command(val direction: String, val value: Int) {
        companion object {
            fun of(input: String): Command =
                input.split(" ").let { Command(it.first(), it.last().toInt()) }
        }
    }
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    val day2Test = Day02(testInput)
    check(day2Test.solvePart1() == 150)
    check(day2Test.solvePart2() == 900)

    val input = readInput("Day02")
    val day2 = Day02(input)
    println(day2.solvePart1())
    println(day2.solvePart2())
}
