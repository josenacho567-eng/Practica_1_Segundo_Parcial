package com.example.practica_1_segundo_parcial

import com.example.practica_1_segundo_parcial.modelMonster.Monster
import com.example.practica_1_segundo_parcial.util.formatearNumero
import com.example.practica_1_segundo_parcial.util.traducirElemento
import com.example.practica_1_segundo_parcial.util.traducirEspecie
import com.example.practica_1_segundo_parcial.util.traducirTipoMonstruo
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class MonsterModelAndFormatoTest {

    @Test
    fun deserializaJsonMhwConCamposNulosSinErrores() {
        val json = """
            [
              {
                "id": 25,
                "type": "large",
                "species": "flying wyvern",
                "elements": ["fire"],
                "name": "Bazelgeuse",
                "description": "A nefarious flying wyvern.",
                "ailments": [
                  {
                    "id": 1,
                    "name": "Blastblight",
                    "description": "Blastblight explosion.",
                    "recovery": {"actions": ["dodge"], "items": []},
                    "protection": {"skills": [{"id": 6, "name": "Blast Resistance", "description": "Grants protection."}], "items": []}
                  }
                ],
                "locations": [{"id": 1, "zoneCount": 17, "name": "Ancient Forest"}],
                "resistances": [{"element": "fire", "condition": null}],
                "weaknesses": [{"element": "thunder", "stars": 3, "condition": null}],
                "rewards": [
                  {
                    "id": 56,
                    "item": {"id": 152, "rarity": 6, "carryLimit": 99, "value": 1200, "name": "Monster Hardbone", "description": "Precious bone."},
                    "conditions": [{"type": "reward", "rank": "high", "quantity": 2, "chance": 11, "subtype": null}]
                  }
                ]
              }
            ]
        """.trimIndent()

        val monsterList = Gson().fromJson(json, Monster::class.java)
        assertEquals(1, monsterList.size)
        val bazel = monsterList.first()
        assertEquals("Bazelgeuse", bazel.name)
        assertNull(bazel.resistances.first().condition)
        assertNull(bazel.weaknesses.first().condition)
        assertNull(bazel.rewards.first().conditions.first().subtype)
    }

    @Test
    fun formatoYTraduccionesFuncionanCorrectamente() {
        assertEquals("#001", formatearNumero(1))
        assertEquals("#025", formatearNumero(25))
        assertEquals("Fuego", traducirElemento("fire"))
        assertEquals("Wyvern Volador", traducirEspecie("flying wyvern"))
        assertEquals("Grande", traducirTipoMonstruo("large"))
    }
}
