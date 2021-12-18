typealias BingoBoard = List<List<Int>>

class Day4(input: List<String>) {
    private val draws: List<Int> = input.first().split(",").map { it.toInt() }
    private val boards: Set<BingoBoard> = parseInput(input)

    private fun parseInput(input: List<String>): Set<BingoBoard> =
        input.asSequence().drop(1).filter { it.isNotEmpty() }.chunked(5).map { parseSingleBoard(it) }.toSet()

    private fun parseSingleBoard(input: List<String>): BingoBoard =
        input.map { row -> row.split(" ").filter { it.isNotEmpty() }.map { it.toInt() } }

    fun solvePart1(): Int {
        val drawn = draws.take(4).toMutableSet()
        return draws.drop(4).firstNotNullOf { draw ->
            drawn += draw
            boards.firstOrNull { it.isWinner(drawn) }?.let { winner -> draw * winner.sumUnmarked(drawn) }
        }
    }

    fun solvePart2(): Int {
        val drawn = draws.toMutableSet()
        return draws.reversed().firstNotNullOf { draw ->
            drawn -= draw
            boards.firstOrNull { !it.isWinner(drawn) }?.let { winner ->
                draw * (winner.sumUnmarked(drawn) - draw)
            }
        }
    }

    private fun BingoBoard.isWinner(draws: Set<Int>): Boolean =
        this.any { row -> row.all { it in draws } } ||
                (0..4).any { col -> this.all { row -> row[col] in draws } }

    private fun BingoBoard.sumUnmarked(draw: Set<Int>): Int =
        this.flatMap { row -> row.filterNot { it in draw } }.sum()

}
fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val day4Test = Day4(testInput)
    check(day4Test.solvePart1() == 4512)
    check(day4Test.solvePart2() == 1924)

    val input = readInput("Day04")
    val day4 = Day4(input)
    println(day4.solvePart1())
    println(day4.solvePart2())
}