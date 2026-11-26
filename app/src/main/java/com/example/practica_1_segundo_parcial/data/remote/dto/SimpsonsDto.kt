package com.example.practica_1_segundo_parcial.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SimpsonsResponseDto(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("prev")
    val prev: String? = null,
    @SerializedName("pages")
    val pages: Int? = null,
    @SerializedName("results")
    val results: List<SimpsonsCharacterDto>? = null
)

data class SimpsonsCharacterDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("age")
    val age: Int? = null,
    @SerializedName("birthdate")
    val birthdate: String? = null,
    @SerializedName("gender")
    val gender: String? = null,
    @SerializedName("occupation")
    val occupation: String? = null,
    @SerializedName("phrases")
    val phrases: List<String>? = null,
    @SerializedName("portrait_path")
    val portraitPath: String? = null,
    @SerializedName("status")
    val status: String? = null
)
