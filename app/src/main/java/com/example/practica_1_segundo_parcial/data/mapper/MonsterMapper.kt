package com.example.practica_1_segundo_parcial.data.mapper

import com.example.practica_1_segundo_parcial.data.remote.dto.AilmentDto
import com.example.practica_1_segundo_parcial.data.remote.dto.ConditionDto
import com.example.practica_1_segundo_parcial.data.remote.dto.ItemDto
import com.example.practica_1_segundo_parcial.data.remote.dto.LocationDto
import com.example.practica_1_segundo_parcial.data.remote.dto.MonsterItemDto
import com.example.practica_1_segundo_parcial.data.remote.dto.ProtectionDto
import com.example.practica_1_segundo_parcial.data.remote.dto.RecoveryDto
import com.example.practica_1_segundo_parcial.data.remote.dto.ResistanceDto
import com.example.practica_1_segundo_parcial.data.remote.dto.RewardDto
import com.example.practica_1_segundo_parcial.data.remote.dto.SkillDto
import com.example.practica_1_segundo_parcial.data.remote.dto.WeaknessDto
import com.example.practica_1_segundo_parcial.modelMonster.Ailment
import com.example.practica_1_segundo_parcial.modelMonster.Condition
import com.example.practica_1_segundo_parcial.modelMonster.Item
import com.example.practica_1_segundo_parcial.modelMonster.Location
import com.example.practica_1_segundo_parcial.modelMonster.MonsterItem
import com.example.practica_1_segundo_parcial.modelMonster.Protection
import com.example.practica_1_segundo_parcial.modelMonster.Recovery
import com.example.practica_1_segundo_parcial.modelMonster.Resistance
import com.example.practica_1_segundo_parcial.modelMonster.Reward
import com.example.practica_1_segundo_parcial.modelMonster.Skill
import com.example.practica_1_segundo_parcial.modelMonster.Weaknesse

fun MonsterItemDto.toDomain(): MonsterItem = MonsterItem(
    id = id ?: 0,
    name = name?.takeIf { it.isNotBlank() } ?: "Desconocido",
    type = type.orEmpty(),
    species = species.orEmpty(),
    description = description.orEmpty(),
    elements = elements.orEmpty(),
    ailments = ailments.orEmpty().map { it.toDomain() },
    locations = locations.orEmpty().map { it.toDomain() },
    resistances = resistances.orEmpty().map { it.toDomain() },
    weaknesses = weaknesses.orEmpty().map { it.toDomain() },
    rewards = rewards.orEmpty().map { it.toDomain() }
)

fun AilmentDto.toDomain(): Ailment = Ailment(
    id = id ?: 0,
    name = name.orEmpty(),
    description = description.orEmpty(),
    protection = protection?.toDomain() ?: Protection(items = emptyList(), skills = emptyList()),
    recovery = recovery?.toDomain() ?: Recovery(actions = emptyList(), items = emptyList())
)

fun ProtectionDto.toDomain(): Protection = Protection(
    items = items.orEmpty().map { it.toDomain() },
    skills = skills.orEmpty().map { it.toDomain() }
)

fun RecoveryDto.toDomain(): Recovery = Recovery(
    actions = actions.orEmpty(),
    items = items.orEmpty().map { it.toDomain() }
)

fun ItemDto.toDomain(): Item = Item(
    id = id ?: 0,
    name = name.orEmpty(),
    description = description.orEmpty(),
    rarity = rarity ?: 0,
    carryLimit = carryLimit ?: 0,
    value = value ?: 0
)

fun SkillDto.toDomain(): Skill = Skill(
    id = id ?: 0,
    name = name.orEmpty(),
    description = description.orEmpty()
)

fun LocationDto.toDomain(): Location = Location(
    id = id ?: 0,
    name = name.orEmpty(),
    zoneCount = zoneCount ?: 0
)

fun ResistanceDto.toDomain(): Resistance = Resistance(
    condition = condition,
    element = element.orEmpty()
)

fun WeaknessDto.toDomain(): Weaknesse = Weaknesse(
    condition = condition,
    element = element.orEmpty(),
    stars = stars ?: 0
)

fun RewardDto.toDomain(): Reward = Reward(
    id = id ?: 0,
    item = item?.toDomain() ?: Item(carryLimit = 0, description = "", id = 0, name = "", rarity = 0, value = 0),
    conditions = conditions.orEmpty().map { it.toDomain() }
)

fun ConditionDto.toDomain(): Condition = Condition(
    chance = chance ?: 0,
    quantity = quantity ?: 0,
    rank = rank.orEmpty(),
    subtype = subtype,
    type = type.orEmpty()
)
