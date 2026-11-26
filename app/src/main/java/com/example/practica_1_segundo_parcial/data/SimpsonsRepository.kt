package com.example.practica_1_segundo_parcial.data

import com.example.practica_1_segundo_parcial.data.mapper.toDomain
import com.example.practica_1_segundo_parcial.data.remote.RetrofitClient
import com.example.practica_1_segundo_parcial.data.remote.SimpsonsApiService
import com.example.practica_1_segundo_parcial.model.SimpsonsResponse

class SimpsonsRepository(private val api: SimpsonsApiService) {
    suspend fun getCharacters(page: Int): SimpsonsResponse = api.getCharacters(page).toDomain()

    companion object {
        val instance by lazy {
            SimpsonsRepository(RetrofitClient.simpsonsApi)
        }
    }
}
