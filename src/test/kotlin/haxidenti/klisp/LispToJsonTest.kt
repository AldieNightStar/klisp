package haxidenti.klisp

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LispToJsonTest {
    private fun parse(src: String): List<LispData> {
        return LispParser.parse(LispLexer.lexAll(src))
    }

    @Test
    fun test() {
        val code = parse("(Walter, 1, 2 (+ 10 20))")
        val string = LispToJson.convertToStr(code)

        Assertions.assertEquals("[{\".call\":[{\".word\":\"Walter\"},1.0,2.0,{\".call\":[{\".word\":\"+\"},10.0,20.0]}]}]", string)
    }
}