package haxidenti.klisp

sealed class LispData;

class LispValue(val value: Any) : LispData()
class LispNode(
    val values: MutableList<LispData> = mutableListOf(),
    val line: Int,
) : LispData()
class LispWord(val name: String) : LispData()

data object LispNull : LispData()

// ==================================

enum class LispTokenType { WORD, SPACE, STRING, BRACKET, COMMENT, NUMBER, DIVIDER }

data class LispToken(
    val data: String,
    val size: Int,
    val type: LispTokenType,
) {
    val eols get() = data.count { it == '\n' }
}