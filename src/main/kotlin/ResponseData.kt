import com.google.api.services.translate.Translate
import com.google.gson.Gson

data class ResponseData(val translations: List<Translations>)

data class Result(val data: ResponseData)

data class Translations(val translatedText: String?)

data class Arguments(val arg1: Int, val arg2: Int): CustomSerializer{

    override fun toString(): String {
        return "'${Gson().toJson(this)}'"
    }
}

interface CustomSerializer



fun replaceLast(text: String, source: String, target: String?): String {
    val b: StringBuilder = StringBuilder(text)
    b.replace(text.lastIndexOf(source), text.lastIndexOf(source) + source.length, target)
    return b.toString()
}