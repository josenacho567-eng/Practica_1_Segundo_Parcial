package com.example.practica_1_segundo_parcial.feature.trivia.data.repository

import com.example.practica_1_segundo_parcial.data.mapper.toDomain
import com.example.practica_1_segundo_parcial.feature.trivia.data.model.TriviaQuestion
import com.example.practica_1_segundo_parcial.feature.trivia.data.remote.TriviaApiService

class TriviaRepository(
    private val apiService: TriviaApiService
) {
    suspend fun getQuestions(amount: Int = 10): List<TriviaQuestion> {
        val response = apiService.getQuestions(amount).toDomain()

        if (response.responseCode != 0) {
            throw Exception("La API respondió con el código ${response.responseCode}")
        }

        return response.results
    }
}
