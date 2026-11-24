package com.example.practica_1_segundo_parcial.data

import com.example.practica_1_segundo_parcial.data.remote.DisneyApiService
import com.example.practica_1_segundo_parcial.data.remote.RetrofitClient

class DisneyRepository(private val api: DisneyApiService) {
    suspend fun characters(page: Int) = api.characters(page)

    companion object {
        val instance by lazy {
            DisneyRepository(RetrofitClient.service("https://api.disneyapi.dev/", DisneyApiService::class.java))
        }
    }
}
