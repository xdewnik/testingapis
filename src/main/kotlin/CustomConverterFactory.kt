import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import java.io.IOException

import com.google.gson.stream.JsonReader

import com.google.gson.stream.JsonWriter

import com.google.gson.TypeAdapter

import com.google.gson.Gson

import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.JsonPrimitive





abstract class CustomizedTypeAdapterFactory<C>(private val customizedClass: Class<C>) :
    TypeAdapterFactory {
    // we use a runtime check to guarantee that 'C' and 'T' are equal
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        return if (type.getRawType() === customizedClass) customizeMyClassAdapter(
            gson,
            type as TypeToken<C>
        ) as TypeAdapter<T> else null
    }

    private fun customizeMyClassAdapter(gson: Gson, type: TypeToken<C>): TypeAdapter<C> {
        val delegate: TypeAdapter<C> = gson.getDelegateAdapter(this, type)
        val elementAdapter = gson.getAdapter(JsonElement::class.java)
        return object : TypeAdapter<C>() {
            @kotlin.jvm.Throws(IOException::class)
            override fun write(out: JsonWriter, value: C) {
                val tree = delegate.toJsonTree(value)
                beforeWrite(value, tree)
                elementAdapter.write(out, tree)
            }

            @kotlin.jvm.Throws(IOException::class)
            override fun read(`in`: JsonReader): C {
                val tree = elementAdapter.read(`in`)
                afterRead(tree)
                return delegate.fromJsonTree(tree)
            }
        }
    }

    /**
     * Override this to muck with `toSerialize` before it is written to
     * the outgoing JSON stream.
     */
    open fun beforeWrite(source: C, toSerialize: JsonElement?) {
    }

    /**
     * Override this to muck with `deserialized` before it parsed into
     * the application type.
     */
    open fun afterRead(deserialized: JsonElement?) {
    }
}

class AaaCustom : CustomizedTypeAdapterFactory<ExecuteData>(ExecuteData::class.java) {

    override fun beforeWrite(source: ExecuteData, toSerialize: JsonElement?) {
        val custom = toSerialize!!.asJsonObject["args"].toString()
        toSerialize.asJsonObject.addProperty("args", custom.replace("\"", "'"))
        toSerialize.asJsonObject

    }

    override fun afterRead(deserialized: JsonElement?) {
    }
}