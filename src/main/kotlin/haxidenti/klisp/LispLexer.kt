package haxidenti.klisp

private const val SPACES = " \t\r\n"
private const val BRACKETS = "()[]{}"
private const val NUMBERS = "0123456789"
private const val WORD_BREAKER = ",;$BRACKETS$SPACES"

object LispLexer {

    fun lexAll(line: String): List<LispToken> {
        val list = mutableListOf<LispToken>()
        var pos = 0
        while (pos < line.length) {
            val substring = line.substring(pos)
            val tok = lexOne(substring)
            pos += tok.size
            list.add(tok)
        }
        return list
    }

    fun lexOne(line: String): LispToken {
        lexSpace(line)?.run { return this }
        lexString(line)?.run { return this }
        lexComment(line)?.run { return this }
        lexKeyword(line)?.run { return this }
        lexBracket(line)?.run { return this }
        lexNumber(line)?.run { return this }
        lexComma(line)?.run { return this }
        lexWord(line)?.run { return this }

        throw IllegalStateException("Can't find such a token type for: $line")
    }

    fun lexSpace(line: String): LispToken? {
        var count = 0
        for (c in line) {
            if (c !in SPACES) {
                break
            }
            count += 1
        }
        if (count > 0) {
            return LispToken(line.substring(0, count), count, LispTokenType.SPACE)
        } else {
            return null
        }
    }

    fun lexString(line: String): LispToken? {
        val quote = line[0]
        if (!eqEither(quote, '"', '\'')) return null

        val sb = StringBuilder()
        var count = 1
        var escaped = false

        for (c in line.substring(1)) {
            count += 1

            if (escaped) {
                val escChar = when (c) {
                    'n' -> '\n'
                    't' -> '\t'
                    'r' -> '\r'
                    '0' -> 0
                    else -> c
                }
                sb.append(escChar)
                escaped = false
                continue
            }

            if (c == '\\') {
                escaped = true
                continue
            }

            if (c == quote) {
                break
            }

            sb.append(c)
        }

        return LispToken(sb.toString(), count, LispTokenType.STRING)
    }

    fun lexWord(line: String): LispToken? {
        var count = 0
        for (c in line) {
            if (c in WORD_BREAKER) break
            count += 1
        }
        if (count < 1) return null
        return LispToken(line.substring(0, count), count, LispTokenType.WORD)
    }

    fun lexComma(line: String): LispToken? {
        if (line.startsWith(",")) {
            return LispToken(",", 1, LispTokenType.DIVIDER)
        }
        return null
    }

    fun lexBracket(line: String): LispToken? {
        val c = line[0]
        if (c in BRACKETS) {
            return LispToken(c.toString(), 1, LispTokenType.BRACKET)
        }
        return null
    }

    fun lexKeyword(line: String): LispToken? {
        if (line[0] != ':') return null
        val word = lexWord(line.substring(1)) ?: return null
        return LispToken(word.data, word.size + 1, LispTokenType.STRING)
    }

    fun lexComment(line: String): LispToken? {
        if (line[0] != ';') return null
        var count = 1
        for (c in line.substring(1)) {
            if (c == '\n') {
                break
            }
            count += 1
        }
        return LispToken(line.substring(1, count).trim(), count, LispTokenType.COMMENT)
    }

    fun lexNumber(line: String): LispToken? {
        var usedDot = false
        var count = 0
        var lexingString = line
        for ((id, c) in lexingString.withIndex()) {
            if (id == 0 && c == '-') {
                count += 1
                continue
            }
            if (c == '.' && !usedDot) {
                usedDot = true
                count += 1
                continue
            }
            if (c !in NUMBERS) {
                break
            }
            count += 1
        }
        if (count == 1 && line[0] in "-.") return null
        if (count < 1) return null
        return LispToken(line.substring(0, count), count, LispTokenType.NUMBER)
    }

    private fun <T> eqEither(n: T, vararg values: T): Boolean {
        for (value in values) {
            if (n == value) return true
        }
        return false
    }
}