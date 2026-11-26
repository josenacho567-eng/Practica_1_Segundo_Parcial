package com.example.practica_1_segundo_parcial.data.remote

import com.example.practica_1_segundo_parcial.data.remote.dto.MonsterItemDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MonsterApiService {
    @GET("monsters")
    suspend fun obtenerMonstruos(): List<MonsterItemDto>

    @GET("monsters/{id}")
    suspend fun obtenerMonstruo(@Path("id") id: Int): MonsterItemDto
}
