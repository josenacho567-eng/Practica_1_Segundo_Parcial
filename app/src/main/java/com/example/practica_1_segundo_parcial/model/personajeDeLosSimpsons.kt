package com.example.practica_1_segundo_parcial.model

data class personajeDeLosSimpsons(
    val id: Int,
    val name: String,
    val age: Int?,
    val birthdate: String?,
    val gender: String?,
    val occupation: String?,
    val phrases: List<String>?,
    val portrait_path: String?,
    val status: String?
) {
    val imageUrl: String?
        get() = portrait_path?.let {
            if (it.startsWith("http")) it else "https://cdn.thesimpsonsapi.com/500$it"
        }
}
