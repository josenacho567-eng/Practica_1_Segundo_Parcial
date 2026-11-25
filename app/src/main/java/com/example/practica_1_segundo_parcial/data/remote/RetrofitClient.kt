package com.example.practica_1_segundo_parcial.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** Cliente centralizado de Retrofit con interceptor de logs HTTP para OkHttp. */
object RetrofitClient {

    private const val MHW_BASE_URL = "https://mhw-db.com/"

    private val clienteHttp: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()

    val monsterApi: MonsterApiService by lazy {
        service(MHW_BASE_URL, MonsterApiService::class.java)
    }

    fun <T> service(baseUrl: String, type: Class<T>): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(clienteHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(type)
}
