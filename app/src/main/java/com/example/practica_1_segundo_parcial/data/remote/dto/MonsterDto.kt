package com.example.practica_1_segundo_parcial.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MonsterItemDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("species")
    val species: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("elements")
    val elements: List<String>? = null,
    @SerializedName("ailments")
    val ailments: List<AilmentDto>? = null,
    @SerializedName("locations")
    val locations: List<LocationDto>? = null,
    @SerializedName("resistances")
    val resistances: List<ResistanceDto>? = null,
    @SerializedName("weaknesses")
    val weaknesses: List<WeaknessDto>? = null,
    @SerializedName("rewards")
    val rewards: List<RewardDto>? = null
)

data class AilmentDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("protection")
    val protection: ProtectionDto? = null,
    @SerializedName("recovery")
    val recovery: RecoveryDto? = null
)

data class ProtectionDto(
    @SerializedName("items")
    val items: List<ItemDto>? = null,
    @SerializedName("skills")
    val skills: List<SkillDto>? = null
)

data class RecoveryDto(
    @SerializedName("actions")
    val actions: List<String>? = null,
    @SerializedName("items")
    val items: List<ItemDto>? = null
)

data class ItemDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("rarity")
    val rarity: Int? = null,
    @SerializedName("carryLimit")
    val carryLimit: Int? = null,
    @SerializedName("value")
    val value: Int? = null
)

data class SkillDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("description")
    val description: String? = null
)

data class LocationDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("zoneCount")
    val zoneCount: Int? = null
)

data class ResistanceDto(
    @SerializedName("element")
    val element: String? = null,
    @SerializedName("condition")
    val condition: String? = null
)

data class WeaknessDto(
    @SerializedName("element")
    val element: String? = null,
    @SerializedName("stars")
    val stars: Int? = null,
    @SerializedName("condition")
    val condition: String? = null
)

data class RewardDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("item")
    val item: ItemDto? = null,
    @SerializedName("conditions")
    val conditions: List<ConditionDto>? = null
)

data class ConditionDto(
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("subtype")
    val subtype: String? = null,
    @SerializedName("rank")
    val rank: String? = null,
    @SerializedName("quantity")
    val quantity: Int? = null,
    @SerializedName("chance")
    val chance: Int? = null
)
