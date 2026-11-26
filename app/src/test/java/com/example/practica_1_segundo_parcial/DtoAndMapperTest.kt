package com.example.practica_1_segundo_parcial

import com.example.practica_1_segundo_parcial.data.mapper.toDomain
import com.example.practica_1_segundo_parcial.data.remote.dto.DisneyResponseDto
import com.example.practica_1_segundo_parcial.data.remote.dto.MonsterItemDto
import com.example.practica_1_segundo_parcial.data.remote.dto.SimpsonsResponseDto
import com.example.practica_1_segundo_parcial.data.remote.dto.TriviaResponseDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class DtoAndMapperTest {

    private val gson = Gson()

    @Test
    fun disneyDtoMapeaCorrectamenteADominio() {
        val json = """
            {
              "info": { "count": 1, "totalPages": 149, "previousPage": null, "nextPage": "https://api.disneyapi.dev/character?page=2" },
              "data": [
                {
                  "_id": 112,
                  "name": "Mickey Mouse",
                  "imageUrl": "https://example.com/mickey.png",
                  "films": ["Fantasia"],
                  "shortFilms": null,
                  "tvShows": ["Mickey Mouse Clubhouse"],
                  "videoGames": [],
                  "parkAttractions": [],
                  "allies": ["Pluto"],
                  "enemies": ["Pete"],
                  "url": "https://api.disneyapi.dev/characters/112"
                }
              ]
            }
        """.trimIndent()

        val dto = gson.fromJson(json, DisneyResponseDto::class.java)
        val domain = dto.toDomain()

        assertEquals(149, domain.info.totalPages)
        assertEquals(1, domain.data.size)
        val character = domain.data.first()
        assertEquals(112, character._id)
        assertEquals("Mickey Mouse", character.name)
        assertEquals(listOf("Fantasia"), character.films)
        assertEquals(emptyList<String>(), character.shortFilms)
    }

    @Test
    fun simpsonsDtoMapeaYConstruyeUrlDeImagen() {
        val json = """
            {
              "count": 1182,
              "next": "https://thesimpsonsapi.com/api/characters?page=2",
              "prev": null,
              "pages": 60,
              "results": [
                {
                  "id": 1,
                  "name": "Homer Simpson",
                  "age": 39,
                  "birthdate": "1956-05-12",
                  "gender": "Male",
                  "occupation": "Safety Inspector",
                  "phrases": ["Doh!"],
                  "portrait_path": "/character/1.webp",
                  "status": "Alive"
                }
              ]
            }
        """.trimIndent()

        val dto = gson.fromJson(json, SimpsonsResponseDto::class.java)
        val domain = dto.toDomain()

        assertEquals(60, domain.pages)
        val homer = domain.results.first()
        assertEquals(1, homer.id)
        assertEquals("Homer Simpson", homer.name)
        assertEquals("https://cdn.thesimpsonsapi.com/500/character/1.webp", homer.imageUrl)
        assertEquals(listOf("Doh!"), homer.phrases)
    }

    @Test
    fun monsterDtoMapeaListaConCamposNulosADominio() {
        val json = """
            [
              {
                "id": 25,
                "type": "large",
                "species": "flying wyvern",
                "elements": ["fire"],
                "name": "Bazelgeuse",
                "description": "A nefarious flying wyvern.",
                "ailments": null,
                "locations": [{"id": 1, "zoneCount": 17, "name": "Ancient Forest"}],
                "resistances": [{"element": "fire", "condition": null}],
                "weaknesses": [{"element": "thunder", "stars": 3, "condition": null}],
                "rewards": []
              }
            ]
        """.trimIndent()

        val type = object : TypeToken<List<MonsterItemDto>>() {}.type
        val dtoList: List<MonsterItemDto> = gson.fromJson(json, type)
        val domainList = dtoList.map { it.toDomain() }

        assertEquals(1, domainList.size)
        val bazel = domainList.first()
        assertEquals(25, bazel.id)
        assertEquals("Bazelgeuse", bazel.name)
        assertEquals(emptyList<Any>(), bazel.ailments)
        assertNull(bazel.resistances.first().condition)
        assertEquals(3, bazel.weaknesses.first().stars)
    }

    @Test
    fun triviaDtoMapeaCorrectamenteADominio() {
        val json = """
            {
              "response_code": 0,
              "results": [
                {
                  "category": "Entertainment: Video Games",
                  "type": "multiple",
                  "difficulty": "medium",
                  "question": "Who is Mario?",
                  "correct_answer": "A plumber",
                  "incorrect_answers": ["A doctor", "A lawyer", "A chef"]
                }
              ]
            }
        """.trimIndent()

        val dto = gson.fromJson(json, TriviaResponseDto::class.java)
        val domain = dto.toDomain()

        assertEquals(0, domain.responseCode)
        assertEquals(1, domain.results.size)
        val q = domain.results.first()
        assertEquals("A plumber", q.correctAnswer)
        assertEquals(3, q.incorrectAnswers.size)
    }
}
