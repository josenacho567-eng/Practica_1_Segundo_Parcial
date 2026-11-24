package com.example.practica_1_segundo_parcial.modelMonster

data class Ailment(
    val description: String,
    val id: Int,
    val name: String,
    val protection: Protection,
    val recovery: Recovery
)