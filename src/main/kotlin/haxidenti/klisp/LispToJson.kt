package haxidenti.klisp

import com.google.gson.*

private val gson = Gson()

object LispToJson {
    fun convertToStr(dat: List<LispData>) = JsonArray().also { dat.map(this::convert).forEach(it::add) }.toString()

    fun convert(dat: LispData): JsonElement {
        return when (dat) {
            is LispValue -> gson.toJsonTree(dat.value)
            is LispNode -> convertNodeToJson(dat)
            is LispWord -> JsonObject().also { it.addProperty(".word", dat.name) }
            is LispNull -> JsonNull.INSTANCE
        }
    }

    private fun convertNodeToJson(node: LispNode): JsonElement {
        val obj = JsonObject()
        val arr = JsonArray().also { node.values.map(this::convert).forEach(it::add) }
        obj.add(".call", arr)
        return obj
    }
}