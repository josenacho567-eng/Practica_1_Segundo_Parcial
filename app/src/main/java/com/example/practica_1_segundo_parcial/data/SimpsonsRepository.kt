package com.example.practica_1_segundo_parcial.data

import com.example.practica_1_segundo_parcial.data.remote.RetrofitClient
import com.example.practica_1_segundo_parcial.data.remote.SimpsonsApiService

class SimpsonsRepository(private val api: SimpsonsApiService) {
    suspend fun getCharacters(page: Int) = api.getCharacters(page)

    companion object {
        val instance by lazy {
            SimpsonsRepository(
                RetrofitClient.service(
                    "https://thesimpsonsapi.com/api/",
                    SimpsonsApiService::class.java
                )
            )
        }
    }
}
