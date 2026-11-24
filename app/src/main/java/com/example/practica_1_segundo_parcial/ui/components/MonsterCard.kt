package com.example.practica_1_segundo_parcial.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.practica_1_segundo_parcial.modelMonster.MonsterItem
import com.example.practica_1_segundo_parcial.util.formatearNumero
import com.example.practica_1_segundo_parcial.util.traducirEspecie

private val ColorFavorito = Color(0xFFE53935)

private fun iconoElemento(elemento: String?): ImageVector = when (elemento?.lowercase()) {
    "fire" -> Icons.Default.LocalFireDepartment
    "water" -> Icons.Default.WaterDrop
    "thunder" -> Icons.Default.Bolt
    "ice" -> Icons.Default.AcUnit
    "dragon" -> Icons.Default.Whatshot
    "blast" -> Icons.Default.Whatshot
    else -> Icons.Default.Pets
}

/**
 * Tarjeta de un monstruo en la cuadricula de 2 columnas (diseno equivalente a PokemonCard).
 * Muestra emblema tematico segun su elemento/especie, numero, nombre, especie y chips.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MonsterCard(
    monstruo: MonsterItem,
    esFavorito: Boolean,
    onClick: () -> Unit,
    onFavoritoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val elementoPrincipal = monstruo.elements.firstOrNull()
    val colorAcento = if (elementoPrincipal != null) {
        colorDelElemento(elementoPrincipal)
    } else {
        colorDelElemento(monstruo.type)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(colorAcento.copy(alpha = 0.16f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (elementoPrincipal != null) {
                            iconoElemento(elementoPrincipal)
                        } else if (monstruo.type.equals("large", ignoreCase = true)) {
                            Icons.Default.Shield
                        } else {
                            Icons.Default.Pets
                        },
                        contentDescription = monstruo.name,
                        tint = colorAcento,
                        modifier = Modifier.size(38.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = formatearNumero(monstruo.id),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = monstruo.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = traducirEspecie(monstruo.species),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (monstruo.elements.isNotEmpty()) {
                        monstruo.elements.take(2).forEach { elemento ->
                            ElementoChip(elemento = elemento, compacto = true)
                        }
                    } else {
                        ElementoChip(elemento = monstruo.type, compacto = true)
                    }
                }
            }

            IconButton(
                onClick = onFavoritoClick,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = if (esFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (esFavorito) "Quitar de favoritos" else "Agregar a favoritos",
                    tint = if (esFavorito) ColorFavorito else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
