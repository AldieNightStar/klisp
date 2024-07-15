package haxidenti.klisp

object LispTreeProcessor {
    fun process(tokens: List<LispData>): List<LispData> {
        return tokens.mapNotNull { processSingle(it) }
    }

    private fun processSingle(d: LispData): LispData? {
        return when (d) {
            is LispNode -> processCall(d)
            else -> d
        }
    }

    private fun processCall(node: LispNode): LispNode? {
        if (node.values.isEmpty()) return node
        val callee = node.values.first()

        // (comment ...) need to be ignored
        if (callee.isWord("comment")) return null

        // Return new node with processed sub nodes ALSO
        return LispNode(process(node.values).toMutableList(), node.line)
    }

    private fun LispData.isWord(name: String): Boolean {
        return this is LispWord && this.name == name
    }
}