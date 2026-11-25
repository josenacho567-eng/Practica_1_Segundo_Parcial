package com.example.practica_1_segundo_parcial.data.remote

import com.example.practica_1_segundo_parcial.feature.trivia.data.remote.TriviaApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** Cliente centralizado de Retrofit con interceptor de logs HTTP para OkHttp. */
object RetrofitClient {

    private const val DISNEY_BASE_URL = "https://api.disneyapi.dev/"
    private const val SIMPSONS_BASE_URL = "https://thesimpsonsapi.com/api/"
    private const val MHW_BASE_URL = "https://mhw-db.com/"

    private const val TRIVIA_BASE_URL = "https://opentdb.com/"

    private val clienteHttp: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()

    val disneyApi: DisneyApiService by lazy {
        service(DISNEY_BASE_URL, DisneyApiService::class.java)
    }

    val simpsonsApi: SimpsonsApiService by lazy {
        service(SIMPSONS_BASE_URL, SimpsonsApiService::class.java)
    }

    val monsterApi: MonsterApiService by lazy {
        service(MHW_BASE_URL, MonsterApiService::class.java)
    }

    val triviaApi: TriviaApiService by lazy {
        service(TRIVIA_BASE_URL, TriviaApiService::class.java)
    }

    private fun <T> service(baseUrl: String, type: Class<T>): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(clienteHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(type)
}
