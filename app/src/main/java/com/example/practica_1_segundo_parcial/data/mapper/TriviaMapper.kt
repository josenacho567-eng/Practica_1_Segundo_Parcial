package com.example.practica_1_segundo_parcial.data.mapper

import com.example.practica_1_segundo_parcial.data.remote.dto.TriviaQuestionDto
import com.example.practica_1_segundo_parcial.data.remote.dto.TriviaResponseDto
import com.example.practica_1_segundo_parcial.feature.trivia.data.model.TriviaQuestion
import com.example.practica_1_segundo_parcial.feature.trivia.data.model.TriviaResponse

fun TriviaResponseDto.toDomain(): TriviaResponse = TriviaResponse(
    responseCode = responseCode ?: -1,
    results = results.orEmpty().map { it.toDomain() }
)

fun TriviaQuestionDto.toDomain(): TriviaQuestion = TriviaQuestion(
    category = category.orEmpty(),
    type = type.orEmpty(),
    difficulty = difficulty.orEmpty(),
    question = question.orEmpty(),
    correctAnswer = correctAnswer.orEmpty(),
    incorrectAnswers = incorrectAnswers.orEmpty()
)
