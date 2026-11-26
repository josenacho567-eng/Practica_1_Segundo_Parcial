package com.example.practica_1_segundo_parcial.data.remote

import com.example.practica_1_segundo_parcial.data.remote.dto.SimpsonsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SimpsonsApiService {
    @GET("characters")
    suspend fun getCharacters(@Query("page") page: Int): SimpsonsResponseDto
}
