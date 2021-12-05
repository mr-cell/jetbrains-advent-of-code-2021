fun main() {
    fun readBingoInput(input: List<String>): List<Int> {
        if (input.isEmpty()) {
            return listOf()
        }

        return input[0].split(",").map { it.toInt() }
    }

    fun readBingoBoards(input: List<String>): List<BingoBoard> {
        val bingoBoards = mutableListOf<BingoBoard>()
        var bingoFields: MutableList<BingoBoardField> = mutableListOf()
        for ((index, entry) in input.withIndex()) {
            if (index < 1) { // omitting first row
                continue
            }

            if (entry == "" && bingoFields.isNotEmpty()) {
                bingoBoards.add(BingoBoard(5, bingoFields))
                bingoFields = mutableListOf()
            }
            bingoFields.addAll(entry.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
                .map { BingoBoardField(it) })
        }

        if (bingoFields.isNotEmpty()) {
            bingoBoards.add(BingoBoard(5, bingoFields))
        }
        return bingoBoards
    }

    fun part1(input: List<String>): Int {
        val bingoInput = readBingoInput(input)
        val bingoBoards = readBingoBoards(input)

        for (value in bingoInput) {
            bingoBoards.forEach { board ->
                board.markFields(value)
                if (board.isBingo()) {
                    return board.calculateSumOfUnmarked() * value
                }
            }
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        val bingoInput = readBingoInput(input)
        val bingoBoards = readBingoBoards(input)

        var lastBingoBoardToWin: BingoBoard? = null
        for (value in bingoInput) {
            bingoBoards.forEach { board ->
                if (!board.isAlreadyMarkedAsBingo()) {
                    board.markFields(value)
                    if (board.isBingo()) {
                        board.markAsBingo(value)
                        lastBingoBoardToWin = board
                    }
                }
            }
        }

        return if (lastBingoBoardToWin != null) {
            lastBingoBoardToWin!!.calculateSumOfUnmarked() * lastBingoBoardToWin!!.bingoValue
        } else {
            0
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

class BingoBoard(val dimension: Int, private val fields: List<BingoBoardField>) {
    var bingoValue: Int = 0
        private set
    private var isBingo = false

    fun markFields(value: Int) {
        fields.filter { it.value == value }.forEach { it.mark() }
    }

    fun isBingo(): Boolean {
        return isHorizontalBingo() || isVerticalBingo() || isDiagonalBingo()
    }

    fun isAlreadyMarkedAsBingo(): Boolean = isBingo

    fun markAsBingo(bingoValue: Int) {
        isBingo = true
        this.bingoValue = bingoValue
    }

    private fun isHorizontalBingo(): Boolean {
        var offset = 0
        for (i in 0 until dimension) {
            var isBingo = true
            for (j in offset until offset + dimension) {
                if (!fields[j].isMarked()) {
                    isBingo = false
                    break
                }
            }
            if (isBingo) {
                return true
            }
            offset += dimension
        }
        return false
    }

    private fun isVerticalBingo(): Boolean {
        for (i in 0 until dimension) {
            var isBingo = true
            for (j in i..dimension * (dimension - 1) + i step dimension) {
                if (!fields[j].isMarked()) {
                    isBingo = false
                    break
                }
            }
            if (isBingo) {
                return true
            }
        }
        return false
    }

    /*
    0   1   2   3   4
    5   6   7   8   9
    10  11  12  13  14
    15  16  17  18  19
    20  21  22  23  24
     */

    private fun isDiagonalBingo(): Boolean {
        var isBingo = true
        for (i in 0 until dimension) {
            if (!fields[i + (i * dimension)].isMarked()) {
                isBingo = false
            }
        }
        if (isBingo) {
            return true
        }

        isBingo = true
        for (i in 0 until dimension) {
            for (j in (dimension - 1) downTo 0) {
                if (!fields[i + (j * dimension)].isMarked()) {
                    isBingo = false
                }
            }
        }
        if (isBingo) {
            return true
        }
        return false
    }

    fun calculateSumOfUnmarked(): Int = fields.filter { !it.isMarked() }.sumOf { it.value }
}

class BingoBoardField(val value: Int) {
    private var marked = false

    fun mark() {
        marked = true
    }

    fun isMarked() = marked
}
