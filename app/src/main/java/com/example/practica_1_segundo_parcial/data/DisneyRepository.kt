package com.example.practica_1_segundo_parcial.data

import com.example.practica_1_segundo_parcial.data.mapper.toDomain
import com.example.practica_1_segundo_parcial.data.remote.DisneyApiService
import com.example.practica_1_segundo_parcial.data.remote.RetrofitClient
import com.example.practica_1_segundo_parcial.model.disneyCharacter

class DisneyRepository(private val api: DisneyApiService) {
    suspend fun characters(page: Int): disneyCharacter = api.characters(page).toDomain()

    companion object {
        val instance by lazy {
            DisneyRepository(RetrofitClient.disneyApi)
        }
    }
}
