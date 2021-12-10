import java.util.*
import kotlin.math.floor

fun main() {

    fun part1(inputData: List<String>): Long {
        var counter = 0L
        for (entry in inputData) {
            val entryChars = entry.toCharArray()
            val stack = Stack<Char>()
            for (char in entryChars) {
                when (char) {
                    '(' -> stack.add(')')
                    '[' -> stack.add(']')
                    '{' -> stack.add('}')
                    '<' -> stack.add('>')
                    ')' -> {
                        val last = stack.pop()
                        if (last != char) {
                            counter += 3
                            break
                        }
                    }
                    ']' -> {
                        val last = stack.pop()
                        if (last != char) {
                            counter += 57
                            break
                        }

                    }
                    '}' -> {
                        val last = stack.pop()
                        if (last != char) {
                            counter += 1197
                            break
                        }
                    }
                    '>' -> {
                        val last = stack.pop()
                        if (last != char) {
                            counter += 25137
                            break
                        }
                    }
                }
            }
        }
        return counter
    }

    fun part2(inputData: List<String>): Long {
        val scores = mutableListOf<Long>()
        outer@ for (entry in inputData) {
            val entryChars = entry.toCharArray()
            val stack = Stack<Char>()
            for (char in entryChars) {
                when (char) {
                    '(' -> stack.add(')')
                    '[' -> stack.add(']')
                    '{' -> stack.add('}')
                    '<' -> stack.add('>')
                    ')' -> {
                        val last = stack.pop()
                        if (last != char) {
                            continue@outer
                        }

                    }
                    ']' -> {
                        val last = stack.pop()
                        if (last != char) {
                            continue@outer
                        }
                    }
                    '}' -> {
                        val last = stack.pop()
                        if (last != char) {
                            continue@outer
                        }
                    }
                    '>' -> {
                        val last = stack.pop()
                        if (last != char) {
                            continue@outer
                        }
                    }
                }
            }
            var score = 0L
            while (stack.isNotEmpty()) {
                when (stack.pop()) {
                    ')' -> score = (score * 5) + 1
                    ']' -> score = (score * 5) + 2
                    '}' -> score = (score * 5) + 3
                    '>' -> score = (score * 5) + 4
                }
            }
            scores.add(score)
        }
        scores.sort()
        return scores[floor((scores.size / 2).toDouble()).toInt()]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397L)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}