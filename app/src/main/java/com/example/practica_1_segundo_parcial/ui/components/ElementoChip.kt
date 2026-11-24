package com.example.practica_1_segundo_parcial.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import com.example.practica_1_segundo_parcial.util.capitalizar
import com.example.practica_1_segundo_parcial.util.traducirElemento
import com.example.practica_1_segundo_parcial.util.traducirTipoMonstruo

private val coloresPorElemento = mapOf(
    "fire" to Color(0xFFEE8130),
    "water" to Color(0xFF3B82F6),
    "thunder" to Color(0xFFF7D02C),
    "ice" to Color(0xFF7DD3FC),
    "dragon" to Color(0xFF6F35FC),
    "poison" to Color(0xFFA33EA1),
    "sleep" to Color(0xFF64748B),
    "paralysis" to Color(0xFFEAB308),
    "blast" to Color(0xFFDC2626),
    "stun" to Color(0xFFD97706),
    "large" to Color(0xFF9E2A2B),
    "small" to Color(0xFF4D7C0F)
)

fun colorDelElemento(clave: String): Color =
    coloresPorElemento[clave.lowercase()] ?: Color(0xFF546E7A)

fun etiquetaDelElemento(clave: String): String = when (clave.lowercase()) {
    "large", "small" -> traducirTipoMonstruo(clave)
    else -> traducirElemento(clave).ifBlank { capitalizar(clave) }
}

/** Etiqueta redondeada con el color del elemento o tamano del monstruo. */
@Composable
fun ElementoChip(
    elemento: String,
    modifier: Modifier = Modifier,
    compacto: Boolean = false
) {
    val color = colorDelElemento(elemento)
    val colorTexto = if (color.luminance() > 0.5f) Color.Black else Color.White

    Surface(
        shape = RoundedCornerShape(50),
        color = color,
        modifier = modifier
    ) {
        Text(
            text = etiquetaDelElemento(elemento),
            color = colorTexto,
            style = if (compacto) MaterialTheme.typography.labelSmall else MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(
                horizontal = if (compacto) 10.dp else 14.dp,
                vertical = if (compacto) 4.dp else 6.dp
            )
        )
    }
}
