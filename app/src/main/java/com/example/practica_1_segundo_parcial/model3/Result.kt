package com.example.practica_1_segundo_parcial.model3

data class Result(
    val category: String,
    val correct_answer: String,
    val difficulty: String,
    val tion: String,
    val typeincorrect_answers: List<String>,
    val ques: String
)