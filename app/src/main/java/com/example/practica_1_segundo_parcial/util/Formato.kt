package com.example.practica_1_segundo_parcial.util

/** 1 -> "#001" */
fun formatearNumero(id: Int): String = "#" + id.toString().padStart(3, '0')

/** "flying wyvern" -> "Flying Wyvern" */
fun capitalizar(texto: String): String =
    texto.replace('-', ' ')
        .replace('_', ' ')
        .split(' ')
        .filter { it.isNotBlank() }
        .joinToString(" ") { palabra ->
            palabra.replaceFirstChar { it.uppercase() }
        }

fun traducirElemento(elemento: String): String = when (elemento.lowercase()) {
    "fire" -> "Fuego"
    "water" -> "Agua"
    "thunder" -> "Rayo"
    "ice" -> "Hielo"
    "dragon" -> "Dragón"
    "poison" -> "Veneno"
    "sleep" -> "Sueño"
    "paralysis" -> "Parálisis"
    "blast" -> "Nitro"
    "stun" -> "Aturdimiento"
    else -> capitalizar(elemento)
}

fun traducirTipoMonstruo(tipo: String): String = when (tipo.lowercase()) {
    "large" -> "Grande"
    "small" -> "Pequeño"
    else -> capitalizar(tipo)
}

fun traducirEspecie(especie: String): String = when (especie.lowercase()) {
    "elder dragon" -> "Dragón Anciano"
    "flying wyvern" -> "Wyvern Volador"
    "brute wyvern" -> "Wyvern Bruto"
    "fanged wyvern" -> "Wyvern de Colmillos"
    "piscine wyvern" -> "Wyvern Piscine"
    "bird wyvern" -> "Wyvern Pájaro"
    "fanged beast" -> "Bestia de Colmillos"
    "herbivore" -> "Herbívoro"
    "wingdrake" -> "Dracoalado"
    "neopteron" -> "Neopteron"
    "lynian" -> "Lynian"
    "relict" -> "Relicto"
    "fish" -> "Pez"
    else -> capitalizar(especie)
}

fun traducirRango(rango: String): String = when (rango.lowercase()) {
    "low" -> "Bajo"
    "high" -> "Alto"
    "master" -> "Maestro"
    else -> capitalizar(rango)
}

fun traducirCondicionRecompensa(tipo: String): String = when (tipo.lowercase()) {
    "carve" -> "Despiece"
    "reward" -> "Recompensa"
    "wound" -> "Rotura"
    "investigation" -> "Investigación"
    "capture" -> "Captura"
    "shiny" -> "Brillo"
    "track" -> "Rastro"
    "palico" -> "Camarada"
    "plunderblade" -> "Sable saqueador"
    else -> capitalizar(tipo)
}
