package com.example.practica_1_segundo_parcial.data.remote

import com.example.practica_1_segundo_parcial.modelMonster.Monster
import com.example.practica_1_segundo_parcial.modelMonster.MonsterItem
import retrofit2.http.GET
import retrofit2.http.Path

interface MonsterApiService {
    @GET("monsters")
    suspend fun obtenerMonstruos(): Monster

    @GET("monsters/{id}")
    suspend fun obtenerMonstruo(@Path("id") id: Int): MonsterItem
}
