package com.example.practica_1_segundo_parcial.data.mapper

import com.example.practica_1_segundo_parcial.data.remote.dto.DisneyCharacterDto
import com.example.practica_1_segundo_parcial.data.remote.dto.DisneyInfoDto
import com.example.practica_1_segundo_parcial.data.remote.dto.DisneyResponseDto
import com.example.practica_1_segundo_parcial.model.Data
import com.example.practica_1_segundo_parcial.model.Info
import com.example.practica_1_segundo_parcial.model.disneyCharacter

fun DisneyResponseDto.toDomain(): disneyCharacter = disneyCharacter(
    data = data.orEmpty().map { it.toDomain() },
    info = info?.toDomain() ?: Info(count = 0, nextPage = null, previousPage = null, totalPages = 1)
)

fun DisneyCharacterDto.toDomain(): Data = Data(
    _id = id ?: 0,
    allies = allies.orEmpty(),
    enemies = enemies.orEmpty(),
    films = films.orEmpty(),
    imageUrl = imageUrl?.takeIf { it.isNotBlank() },
    name = name?.takeIf { it.isNotBlank() } ?: "Sin nombre",
    parkAttractions = parkAttractions.orEmpty(),
    shortFilms = shortFilms.orEmpty(),
    tvShows = tvShows.orEmpty(),
    url = url.orEmpty(),
    videoGames = videoGames.orEmpty()
)

fun DisneyInfoDto.toDomain(): Info = Info(
    count = count ?: 0,
    nextPage = nextPage,
    previousPage = previousPage,
    totalPages = (totalPages ?: 1).coerceAtLeast(1)
)
