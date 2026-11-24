package com.example.practica_1_segundo_parcial.modelSimpsons

data class personajeDeLosSimpsons(
    val age: Int,
    val birthdate: String,
    val gender: String,
    val id: Int,
    val name: String,
    val occupation: String,
    val phrases: List<String>,
    val portrait_path: String,
    val status: String
)