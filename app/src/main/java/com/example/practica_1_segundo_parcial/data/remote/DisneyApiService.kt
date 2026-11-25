package com.example.practica_1_segundo_parcial.data.remote

import com.example.practica_1_segundo_parcial.model.disneyCharacter
import retrofit2.http.GET
import retrofit2.http.Query

interface DisneyApiService {
    @GET("character")
    suspend fun characters(@Query("page") page: Int): disneyCharacter
}
