class Day16(input: List<String>) {
    private val binaryInput: Iterator<Char> = hexToBinary(input.first()).iterator()

    private fun hexToBinary(input: String): List<Char> =
        input.map {
            it.digitToInt(16).toString(2).padStart(4, '0')
        }.flatMap { it.toList() }

    fun solvePart1(): Int = BITSPacket.of(binaryInput).allVersions().sum()

    fun solvePart2(): Long = BITSPacket.of(binaryInput).value

    private sealed class BITSPacket(val version: Int) {
        abstract val value: Long

        abstract fun allVersions(): List<Int>

        companion object {
            fun of(input: Iterator<Char>): BITSPacket {
                val version = input.nextInt(3)
                return when (val packetType = input.nextInt(3)) {
                    4 -> BITSLiteral.of(version, input)
                    else -> BITSOperator.of(version, packetType, input)
                }
            }
        }
    }

    private class BITSOperator(version: Int, type: Int, val subpackets: List<BITSPacket>): BITSPacket(version) {
        override val value: Long = when (type) {
            0 -> subpackets.sumOf { it.value }
            1 -> subpackets.fold(1) { acc, next -> acc * next.value }
            2 -> subpackets.minOf { it.value }
            3 -> subpackets.maxOf { it.value }
            5 -> if (subpackets.first().value > subpackets.last().value) 1 else 0
            6 -> if (subpackets.first().value < subpackets.last().value) 1 else 0
            7 -> if (subpackets.first().value == subpackets.last().value) 1 else 0
            else -> error("Invalid Operator type")
        }

        override fun allVersions(): List<Int> = listOf(version) + subpackets.flatMap { it.allVersions() }

        companion object {
            fun of(version: Int, type: Int, input: Iterator<Char>): BITSOperator {
                return when (input.nextInt(1)) {
                    0 -> {
                        val subPacketLength = input.nextInt(15)
                        val subPacketIterator = input.next(subPacketLength).iterator()
                        val subPackets = subPacketIterator.executeUntilEmpty { of(it) }
                        BITSOperator(version, type, subPackets)
                    }
                    1 -> {
                        val numberOfPackets = input.nextInt(11)
                        val subPackets = (1..numberOfPackets).map { of(input) }
                        BITSOperator(version, type, subPackets)
                    }
                    else -> error("Invalid Operator length type")
                }
            }
        }

    }

    private class BITSLiteral(version: Int, override val value: Long) : BITSPacket(version) {
        override fun allVersions(): List<Int> = listOf(version)

        companion object {
            fun of(version: Int, input: Iterator<Char>): BITSLiteral =
                BITSLiteral(version, parseLiteralValue(input))

            private fun parseLiteralValue(input: Iterator<Char>): Long  =
                input.nextUntilFirst(5) { it.startsWith('0') }.map { it.drop(1) }.joinToString("").toLong(2)
        }
    }
}

private fun Iterator<Char>.next(size: Int): String =
    (1..size).map { this.next() }.joinToString("")

fun Iterator<Char>.nextInt(size: Int): Int = next(size).toInt(2)

fun Iterator<Char>.nextUntilFirst(size: Int, stopCondition: (String) -> Boolean): List<String> {
    val output = mutableListOf<String>()
    do {
        val readValue = next(size)
        output.add(readValue)
    } while (!stopCondition(readValue))
    return output
}

fun <T> Iterator<Char>.executeUntilEmpty(function: (Iterator<Char>) -> T): List<T> {
    val output = mutableListOf<T>()
    while (this.hasNext()) {
        output.add(function(this))
    }
    return output
}

fun main() {

    // test if implementation meets criteria from the description, like:
    check(Day16(listOf("8A004A801A8002F478")).solvePart1() == 16)
    check(Day16(listOf("C200B40A82")).solvePart2() == 3L)
    check(Day16(listOf("04005AC33890")).solvePart2() == 54L)
    check(Day16(listOf("880086C3E88112")).solvePart2() == 7L)
    check(Day16(listOf("CE00C43D881120")).solvePart2() == 9L)
    check(Day16(listOf("D8005AC2A8F0")).solvePart2() == 1L)
    check(Day16(listOf("F600BC2D8F")).solvePart2() == 0L)
    check(Day16(listOf("9C005AC2F8F0")).solvePart2() == 0L)
    check(Day16(listOf("9C0141080250320F1802104A08")).solvePart2() == 1L)

    val input = readInput("Day16")
    println(Day16(input).solvePart1())
    println(Day16(input).solvePart2())
}