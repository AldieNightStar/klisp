package haxidenti.klisp

import java.util.*

object LispParser {
    fun parse(tokens: List<LispToken>): List<LispData> {
        val stack = Stack<LispNode>()
        stack.push(LispNode())
        for (token in tokens) {
            if (token.type == LispTokenType.SPACE) continue
            if (token.type == LispTokenType.BRACKET) {
                if (token.data == "(") {
                    stack.add(LispNode())
                } else if (token.data == ")") {
                    val lastNode = stack.pop()
                    stack.peek().values.add(lastNode)
                }
            } else {
                token.toLispData()?.run {
                    stack.peek().values.add(this)
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
        return LispTreeProcessor.process(stack.first().values)
    }

    private fun LispToken.toLispData(): LispData? {
        return when (type) {
            LispTokenType.STRING -> LispValue(this.data)
            LispTokenType.NUMBER -> LispValue(this.data.toDouble())
            LispTokenType.WORD -> when (this.data) {
                "null" -> LispNull
                "nil" -> LispNull
                "true" -> LispValue(true)
                "false" -> LispValue(false)
                else -> LispWord(this.data)
            }

            else -> null
        }
    }
}