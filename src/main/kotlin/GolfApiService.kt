import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface GithubApiService {
    @POST("v2")
    @FormUrlEncoded
    suspend fun translate(
        @Field("q") query: String?,
        @Field("source") fromLang: String,
        @Field("target") targetLand: String,
        @Field("format") format: String = "text",
        @Header("Authorization") auth: String = "Bearer ya29.c."
    ): Result

    /**
     * Factory class for convenient creation of the Api Service interface
     */
    companion object Factory {
        fun create(): GithubApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://translation.googleapis.com/language/translate/")
                .build()
            return retrofit.create(GithubApiService::class.java)
        }
    }
}