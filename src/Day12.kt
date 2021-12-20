typealias Caves = Map<String, List<String>>
class Day12(input: List<String>) {
    private val caves = parseInput(input)

    private fun parseInput(input: List<String>): Caves  =
        input.map { it.split("-") }
            .flatMap { listOf(it.first() to it.last(), it.last() to it.first()) }
            .groupBy({ it.first }, { it.second })

    private fun traverse(
        allowedToVisit: (String, List<String>) -> Boolean,
        path: List<String> = listOf("start")
    ): List<List<String>> =
        if (path.last() == "end") listOf(path)
        else caves.getValue(path.last())
            .filter { allowedToVisit(it, path) }
            .flatMap { traverse(allowedToVisit, path + it) }

    private fun part1VisitRule(name: String, path: List<String>): Boolean =
        name.isUpperCase() || name !in path

    private fun part2VisitRule(name: String, path: List<String>): Boolean =
        when {
            name.isUpperCase() -> true
            name == "start" -> false
            name !in path -> true
            else -> path
                .filterNot { it.isUpperCase() }
                .groupBy { it }
                .none { it.value.size == 2}
        }

    fun solvePart1(): Int  = traverse(::part1VisitRule).size

    fun solvePart2(): Int = traverse(::part2VisitRule).size

    private fun String.isUpperCase(): Boolean = all { it.isUpperCase() }
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    val day12Test = Day12(testInput)
    check(day12Test.solvePart1() == 10)
    check(day12Test.solvePart2() == 36)

    val input = readInput("Day12")
    val day12 = Day12(input)
    println(day12.solvePart1())
    println(day12.solvePart2())
}