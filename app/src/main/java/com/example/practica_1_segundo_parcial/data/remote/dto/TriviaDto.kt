package com.example.practica_1_segundo_parcial.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TriviaResponseDto(
    @SerializedName("response_code")
    val responseCode: Int? = null,
    @SerializedName("results")
    val results: List<TriviaQuestionDto>? = null
)

data class TriviaQuestionDto(
    @SerializedName("category")
    val category: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("difficulty")
    val difficulty: String? = null,
    @SerializedName("question")
    val question: String? = null,
    @SerializedName("correct_answer")
    val correctAnswer: String? = null,
    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>? = null
)
