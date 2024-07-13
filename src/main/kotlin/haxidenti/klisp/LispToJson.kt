package haxidenti.klisp

import com.google.gson.*

private val gson = Gson()

object LispToJson {
    fun convertToStr(dat: List<LispData>) = JsonArray().also { dat.map(this::convert).forEach(it::add) }.toString()

    fun convert(dat: LispData): JsonElement {
        return when (dat) {
            is LispValue -> gson.toJsonTree(dat.value)
            is LispNode -> JsonArray().also { dat.values.map(this::convert).forEach(it::add) }
            is LispWord -> JsonObject().also { it.addProperty(".word", dat.name) }
            is LispNull -> JsonNull.INSTANCE
        }
    }
}