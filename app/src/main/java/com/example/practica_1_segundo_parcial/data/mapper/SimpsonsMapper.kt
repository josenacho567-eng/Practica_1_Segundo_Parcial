package com.example.practica_1_segundo_parcial.data.mapper

import com.example.practica_1_segundo_parcial.data.remote.dto.SimpsonsCharacterDto
import com.example.practica_1_segundo_parcial.data.remote.dto.SimpsonsResponseDto
import com.example.practica_1_segundo_parcial.model.SimpsonsResponse
import com.example.practica_1_segundo_parcial.model.personajeDeLosSimpsons

private const val SIMPSONS_CDN_URL = "https://cdn.thesimpsonsapi.com/500"

fun SimpsonsResponseDto.toDomain(): SimpsonsResponse = SimpsonsResponse(
    count = count ?: 0,
    next = next,
    prev = prev,
    pages = (pages ?: 1).coerceAtLeast(1),
    results = results.orEmpty().map { it.toDomain() }
)

fun SimpsonsCharacterDto.toDomain(): personajeDeLosSimpsons = personajeDeLosSimpsons(
    id = id ?: 0,
    name = name?.takeIf { it.isNotBlank() } ?: "Desconocido",
    age = age,
    birthdate = birthdate?.takeIf { it.isNotBlank() },
    gender = gender?.takeIf { it.isNotBlank() },
    occupation = occupation?.takeIf { it.isNotBlank() },
    phrases = phrases.orEmpty(),
    imageUrl = portraitPath?.takeIf { it.isNotBlank() }?.let { path ->
        if (path.startsWith("http")) path else "$SIMPSONS_CDN_URL$path"
    },
    status = status?.takeIf { it.isNotBlank() }
)
