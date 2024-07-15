package haxidenti.klisp

import java.util.*

object LispParser {
    fun parse(tokens: List<LispToken>): List<LispData> {
        val stack = Stack<MutableList<LispData>>()
        stack.push(mutableListOf())
        for (token in tokens) {
            if (token.type == LispTokenType.SPACE) continue
            if (token.type == LispTokenType.BRACKET) {
                if (token.data == "(") {
                    stack.add(mutableListOf())
                } else if (token.data == ")") {
                    val lastNode = stack.pop().toNode()
                    stack.peek().add(lastNode)
                }
            } else {
                token.toLispData()?.run {
                    stack.peek().add(this)
                }
            }
        }

        val size = stack.size
        if (size > 1) {
            throw IllegalStateException("Too many open brackets")
        } else if (size < 1) {
            throw IllegalStateException("Redundant closing brackets found")
        }

        // Finally process the Tree
        return LispTreeProcessor.process(stack.first())
    }

    private fun MutableList<LispData>.toNode(): LispNode {
        return LispNode(this)
    }

    private fun LispToken.toLispData(): LispData? {
        return when (type) {
            LispTokenType.STRING -> LispValue(this.data)
            LispTokenType.NUMBER -> LispValue(this.data.toDouble())
            LispTokenType.WORD -> when (this.data) {
                "null" -> LispNull
                "true" -> LispValue(true)
                "false" -> LispValue(false)
                else -> LispWord(this.data)
            }

            else -> null
        }
    }
}