package com.example.practica_1_segundo_parcial.modelMonster

data class MonsterItem(
    val ailments: List<Ailment>,
    val description: String,
    val elements: List<String>,
    val id: Int,
    val locations: List<Location>,
    val name: String,
    val resistances: List<Resistance>,
    val rewards: List<Reward>,
    val species: String,
    val type: String,
    val weaknesses: List<Weaknesse>
)