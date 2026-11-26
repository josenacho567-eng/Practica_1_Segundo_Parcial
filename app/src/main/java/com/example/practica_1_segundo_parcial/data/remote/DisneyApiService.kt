package com.example.practica_1_segundo_parcial.data.remote

import com.example.practica_1_segundo_parcial.data.remote.dto.DisneyResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface DisneyApiService {
    @GET("character")
    suspend fun characters(@Query("page") page: Int): DisneyResponseDto
}
