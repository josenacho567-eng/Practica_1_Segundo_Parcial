package com.example.practica_1_segundo_parcial.feature.trivia.data.remote

import com.example.practica_1_segundo_parcial.data.remote.dto.TriviaResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaApiService {
    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int = 10
    ): TriviaResponseDto
}
