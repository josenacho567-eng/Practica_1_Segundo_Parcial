package com.example.practica_1_segundo_parcial.model

data class SimpsonsResponse(
    val count: Int,
    val next: String?,
    val prev: String?,
    val pages: Int,
    val results: List<personajeDeLosSimpsons>
)
