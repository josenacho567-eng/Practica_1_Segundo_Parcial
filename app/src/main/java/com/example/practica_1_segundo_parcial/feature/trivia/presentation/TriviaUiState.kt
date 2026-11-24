package com.example.practica_1_segundo_parcial.feature.trivia.presentation

import com.example.practica_1_segundo_parcial.feature.trivia.data.model.TriviaQuestion

data class TriviaUiState(
    val isLoading: Boolean = false,
    val questions: List<TriviaQuestion> = emptyList(),
    val errorMessage: String? = null
)
