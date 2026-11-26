package com.example.practica_1_segundo_parcial.model

data class personajeDeLosSimpsons(
    val id: Int,
    val name: String,
    val age: Int?,
    val birthdate: String?,
    val gender: String?,
    val occupation: String?,
    val phrases: List<String>,
    val imageUrl: String?,
    val status: String?
)
