package haxidenti.klisp

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LispToJsonTest {
    @Test
    fun test() {
        val src = LispToJson.convert(
            LispNode(
                mutableListOf(
                    LispWord("Walter"),
                    LispValue(1),
                    LispValue(2),
                    LispNode(
                        mutableListOf(
                            LispWord("+"),
                            LispValue(10),
                            LispValue(20),
                        )
                    )
                )
            )
        ).toString()
        println(src)
        assertEquals(
            "{\".call\":[{\".word\":\"Walter\"},1,2,{\".call\":[{\".word\":\"+\"},10,20]}]}",
            src
        )
    }
}