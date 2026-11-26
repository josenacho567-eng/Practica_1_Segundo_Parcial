package com.example.practica_1_segundo_parcial.feature.trivia.data.model

data class TriviaResponse(
    val responseCode: Int,
    val results: List<TriviaQuestion>
)

data class TriviaQuestion(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>
)
