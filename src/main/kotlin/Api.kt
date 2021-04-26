import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header

@JvmSuppressWildcards
interface Testing {

    @GET("v1/content/list")
    suspend fun getContent(
        @Header("X-Api-Token") token: String = "",
        @Header("Device") device: String = ""
    ): List<MediaFile>
}
fun RequestBody?.bodyToString(): String {
    if (this == null) return ""
    val buffer = okio.Buffer()
    writeTo(buffer)
    return buffer.readUtf8()
}
