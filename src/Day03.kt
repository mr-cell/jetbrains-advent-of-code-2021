class Day03(private val input: List<String>) {

    fun solvePart1(): Int {
        val gamma = input.first().indices.map { column ->
            if (input.count { it[column] == '1' } > input.size / 2) '1' else '0'
        }.joinToString(separator = "")

        val epsilon = gamma.map { if(it == '1') '0' else '1' }.joinToString(separator = "")
        return gamma.toInt(2) * epsilon.toInt(2)
    }

    fun solvePart2(): Int =
        input.bitwiseFilter(true).toInt(2) * input.bitwiseFilter(false).toInt(2)

    private fun List<String>.bitwiseFilter(keepMostCommon: Boolean): String =
        first().indices.fold(this) { inputs, column ->
            if (inputs.size == 1) inputs else {
                val split = inputs.partition { it[column] == '1' }
                if (keepMostCommon) split.longest() else split.shortest()
            }
        }.first()

    private fun <T> Pair<List<T>, List<T>>.longest(): List<T> = if (first.size >= second.size) first else second
    private fun <T> Pair<List<T>, List<T>>.shortest(): List<T> = if (first.size < second.size) first else second
}

fun main() {
    val testInput = readInput("Day03_test")
    val day3Test = Day03(testInput)
    check(day3Test.solvePart1() == 198)
    check(day3Test.solvePart2() == 230)

    val input = readInput("Day03")
    val day3 = Day03(input)
    println(day3.solvePart1())
    println(day3.solvePart2())
}
