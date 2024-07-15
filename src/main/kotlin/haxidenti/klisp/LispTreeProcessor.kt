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

    private fun processCall(d: LispNode): LispNode? {
        if (d.values.isEmpty()) return d
        val callee = d.values.first()

        // (comment ...) need to be ignored
        if (callee.isWord("comment")) return null

        // Return new node with processed sub nodes ALSO
        return LispNode(process(d.values).toMutableList())
    }

    private fun LispData.isWord(name: String): Boolean {
        return this is LispWord && this.name == name
    }
}