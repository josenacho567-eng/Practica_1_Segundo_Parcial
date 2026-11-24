package com.example.practica_1_segundo_parcial.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.practica_1_segundo_parcial.ui.components.ElementoChip
import com.example.practica_1_segundo_parcial.ui.components.MensajeDeError
import com.example.practica_1_segundo_parcial.ui.components.colorDelElemento
import com.example.practica_1_segundo_parcial.ui.theme.Practica_1_Segundo_ParcialTheme
import com.example.practica_1_segundo_parcial.util.formatearNumero
import com.example.practica_1_segundo_parcial.util.traducirCondicionRecompensa
import com.example.practica_1_segundo_parcial.util.traducirElemento
import com.example.practica_1_segundo_parcial.util.traducirEspecie
import com.example.practica_1_segundo_parcial.util.traducirRango
import com.example.practica_1_segundo_parcial.util.traducirTipoMonstruo

private const val MAX_ESTRELLAS = 3f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonsterDetailScreen(
    onVolver: () -> Unit,
    viewModel: MonsterDetailViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val detalle = uiState.detalle
    val error = uiState.error

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(detalle?.name ?: "Detalle") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    if (detalle != null) {
                        IconButton(onClick = viewModel::alternarFavorito) {
                            Icon(
                                imageVector = if (uiState.esFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = if (uiState.esFavorito) "Quitar de favoritos" else "Agregar a favoritos"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingInterno ->
        val modifier = Modifier.padding(paddingInterno)
        when {
            uiState.cargando -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                MensajeDeError(
                    mensaje = error,
                    onReintentar = viewModel::cargar,
                    modifier = modifier.fillMaxSize()
                )
            }
            detalle != null -> MonsterDetailContent(detalle = detalle, modifier = modifier)
            else -> Unit
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MonsterDetailContent(
    detalle: MonsterItem,
    modifier: Modifier = Modifier
) {
    val colorPrincipal = detalle.elements.firstOrNull()?.let(::colorDelElemento)
        ?: colorDelElemento(detalle.type)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(112.dp)
                .clip(CircleShape)
                .background(colorPrincipal.copy(alpha = 0.16f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (detalle.type.equals("large", ignoreCase = true)) {
                    Icons.Default.Shield
                } else {
                    Icons.Default.Pets
                },
                contentDescription = detalle.name,
                tint = colorPrincipal,
                modifier = Modifier.size(58.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = formatearNumero(detalle.id),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = detalle.name,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${traducirEspecie(detalle.species)} • ${traducirTipoMonstruo(detalle.type)}",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(12.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ElementoChip(elemento = detalle.type)
            detalle.elements.forEach { elemento ->
                ElementoChip(elemento = elemento)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "Descripción",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = detalle.description.ifBlank { "Sin descripción disponible." },
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        if (detalle.locations.isNotEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))
            TituloSeccion("Hábitats")
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                detalle.locations.forEach { loc ->
                    AssistChip(
                        onClick = {},
                        label = { Text("${loc.name} (${loc.zoneCount} zonas)") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    )
                }
            }
        }

        if (detalle.weaknesses.isNotEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))
            TituloSeccion("Debilidades")
            Spacer(modifier = Modifier.height(8.dp))
            detalle.weaknesses.forEach { debilidad ->
                FilaDebilidad(debilidad = debilidad)
            }
        }

        if (detalle.resistances.isNotEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))
            TituloSeccion("Resistencias")
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                detalle.resistances.forEach { resistencia ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        ElementoChip(elemento = resistencia.element)
                        if (!resistencia.condition.isNullOrBlank()) {
                            Text(
                                text = "(${resistencia.condition})",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        if (detalle.ailments.isNotEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))
            TituloSeccion("Estados alterados")
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                detalle.ailments.forEach { estado ->
                    TarjetaAilment(estado)
                }
            }
        }

        if (detalle.rewards.isNotEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))
            TituloSeccion("Recompensas (${detalle.rewards.size})")
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                detalle.rewards.take(10).forEach { recompensa ->
                    TarjetaRecompensa(recompensa)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun TituloSeccion(texto: String) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = texto,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}

@Composable
private fun FilaDebilidad(debilidad: Weaknesse) {
    val color = colorDelElemento(debilidad.element)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.width(124.dp)) {
            Text(
                text = traducirElemento(debilidad.element),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            if (!debilidad.condition.isNullOrBlank()) {
                Text(
                    text = debilidad.condition,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Row(
            modifier = Modifier.width(64.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(debilidad.stars.coerceIn(0, 3)) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        LinearProgressIndicator(
            progress = { (debilidad.stars / MAX_ESTRELLAS).coerceIn(0f, 1f) },
            color = color,
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
        )
    }
}

@Composable
private fun TarjetaAilment(ailment: Ailment) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = ailment.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            if (ailment.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = ailment.description, style = MaterialTheme.typography.bodySmall)
            }
            val curas = buildList {
                if (ailment.recovery.actions.isNotEmpty()) {
                    add("Acción: ${ailment.recovery.actions.joinToString(", ")}")
                }
                if (ailment.recovery.items.isNotEmpty()) {
                    add("Objeto: ${ailment.recovery.items.joinToString(", ") { it.name }}")
                }
                if (ailment.protection.skills.isNotEmpty()) {
                    add("Habilidad: ${ailment.protection.skills.joinToString(", ") { it.name }}")
                }
            }
            if (curas.isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = curas.joinToString(" • "),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun TarjetaRecompensa(reward: Reward) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = reward.item.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Rareza ${reward.item.rarity}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            if (reward.conditions.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                reward.conditions.take(3).forEach { cond ->
                    val sub = if (!cond.subtype.isNullOrBlank()) " (${cond.subtype})" else ""
                    Text(
                        text = "• ${traducirCondicionRecompensa(cond.type)}$sub • Rango ${traducirRango(cond.rank)} • x${cond.quantity} (${cond.chance}%)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MonsterDetailContentPreview() {
    Practica_1_Segundo_ParcialTheme {
        MonsterDetailContent(
            detalle = MonsterItem(
                ailments = listOf(
                    Ailment(
                        description = "Blastblight will cause a damaging explosion.",
                        id = 1,
                        name = "Blastblight",
                        protection = Protection(
                            items = emptyList(),
                            skills = listOf(Skill("Grants protection against blastblight.", 6, "Blast Resistance"))
                        ),
                        recovery = Recovery(actions = listOf("dodge"), items = emptyList())
                    )
                ),
                description = "A nefarious flying wyvern that travels the New World in search of prey.",
                elements = listOf("fire"),
                id = 25,
                locations = listOf(
                    Location(1, "Ancient Forest", 17),
                    Location(5, "Elder's Recess", 16)
                ),
                name = "Bazelgeuse",
                resistances = listOf(Resistance(null, "fire")),
                rewards = listOf(
                    Reward(
                        conditions = listOf(Condition(32, 1, "high", "Body", "carve")),
                        id = 49,
                        item = Item(99, "Rare Bazelgeuse material.", 398, "Bazelgeuse Scale+", 6, 1600)
                    )
                ),
                species = "flying wyvern",
                type = "large",
                weaknesses = listOf(
                    Weaknesse(null, "thunder", 3),
                    Weaknesse(null, "ice", 2),
                    Weaknesse(null, "dragon", 2),
                    Weaknesse(null, "water", 1)
                )
            )
        )
    }
}
