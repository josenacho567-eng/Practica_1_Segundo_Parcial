package com.example.practica_1_segundo_parcial.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practica_1_segundo_parcial.modelMonster.Location
import com.example.practica_1_segundo_parcial.modelMonster.MonsterItem
import com.example.practica_1_segundo_parcial.modelMonster.Weaknesse
import com.example.practica_1_segundo_parcial.ui.components.MensajeDeError
import com.example.practica_1_segundo_parcial.ui.components.MonsterCard
import com.example.practica_1_segundo_parcial.ui.theme.Practica_1_Segundo_ParcialTheme

/**
 * Pantalla principal del bestiario MHW: observa el ViewModel y delega la UI en [MonsterListContent].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonsterListScreen(
    onMonsterClick: (Int) -> Unit,
    onVerFavoritos: () -> Unit,
    viewModel: MonsterListViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Bestiario MHW") },
                actions = {
                    IconButton(onClick = viewModel::recargar) {
                        Icon(Icons.Default.Refresh, contentDescription = "Actualizar")
                    }
                    IconButton(onClick = onVerFavoritos) {
                        Icon(Icons.Default.Favorite, contentDescription = "Ver favoritos")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingInterno ->
        MonsterListContent(
            uiState = uiState,
            onMonsterClick = onMonsterClick,
            onFavoritoClick = viewModel::alternarFavorito,
            onCargarMas = viewModel::cargarMas,
            onReintentar = viewModel::recargar,
            onBusquedaChange = viewModel::actualizarBusqueda,
            onFiltroChange = viewModel::seleccionarFiltro,
            modifier = Modifier.padding(paddingInterno)
        )
    }
}

@Composable
fun MonsterListContent(
    uiState: MonsterListUiState,
    onMonsterClick: (Int) -> Unit,
    onFavoritoClick: (MonsterItem) -> Unit,
    onCargarMas: () -> Unit,
    onReintentar: () -> Unit,
    onBusquedaChange: (String) -> Unit,
    onFiltroChange: (FiltroTipoMonstruo) -> Unit,
    modifier: Modifier = Modifier
) {
    val error = uiState.error

    when {
        uiState.monstruos.isEmpty() && uiState.cargando -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        uiState.monstruos.isEmpty() && error != null -> {
            MensajeDeError(
                mensaje = error,
                onReintentar = onReintentar,
                modifier = modifier.fillMaxSize()
            )
        }
        else -> {
            Column(modifier = modifier.fillMaxSize()) {
                OutlinedTextField(
                    value = uiState.busqueda,
                    onValueChange = onBusquedaChange,
                    placeholder = { Text("Buscar monstruo o especie...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (uiState.busqueda.isNotEmpty()) {
                            IconButton(onClick = { onBusquedaChange("") }) {
                                Icon(Icons.Default.Clear, contentDescription = "Limpiar")
                            }
                        }
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FiltroTipoMonstruo.entries.forEach { filtro ->
                        FilterChip(
                            selected = uiState.filtroTipo == filtro,
                            onClick = { onFiltroChange(filtro) },
                            label = { Text(filtro.etiqueta) }
                        )
                    }
                }

                if (uiState.monstruos.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No se encontraron monstruos con ese filtro.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.monstruos, key = { it.id }) { monstruo ->
                            MonsterCard(
                                monstruo = monstruo,
                                esFavorito = monstruo.id in uiState.favoritosIds,
                                onClick = { onMonsterClick(monstruo.id) },
                                onFavoritoClick = { onFavoritoClick(monstruo) }
                            )
                        }

                        item(span = { GridItemSpan(maxLineSpan) }) {
                            PiePaginacion(uiState = uiState, onCargarMas = onCargarMas, onReintentar = onReintentar)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PiePaginacion(
    uiState: MonsterListUiState,
    onCargarMas: () -> Unit,
    onReintentar: () -> Unit
) {
    val error = uiState.error

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            uiState.cargando -> CircularProgressIndicator()
            error != null -> MensajeDeError(mensaje = error, onReintentar = onReintentar)
            uiState.hayMas -> Button(onClick = onCargarMas) {
                Text("Cargar más (${uiState.monstruos.size}/${uiState.totalFiltrados})")
            }
            else -> Unit
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MonsterListContentPreview() {
    Practica_1_Segundo_ParcialTheme {
        MonsterListContent(
            uiState = MonsterListUiState(
                monstruos = listOf(
                    MonsterItem(
                        ailments = emptyList(),
                        description = "Docile herbivores that graze in packs.",
                        elements = emptyList(),
                        id = 1,
                        locations = listOf(Location(1, "Ancient Forest", 17)),
                        name = "Aptonoth",
                        resistances = emptyList(),
                        rewards = emptyList(),
                        species = "herbivore",
                        type = "small",
                        weaknesses = listOf(Weaknesse(null, "fire", 1))
                    ),
                    MonsterItem(
                        ailments = emptyList(),
                        description = "A nefarious flying wyvern.",
                        elements = listOf("fire"),
                        id = 25,
                        locations = listOf(Location(1, "Ancient Forest", 17)),
                        name = "Bazelgeuse",
                        resistances = emptyList(),
                        rewards = emptyList(),
                        species = "flying wyvern",
                        type = "large",
                        weaknesses = listOf(Weaknesse(null, "thunder", 3))
                    ),
                    MonsterItem(
                        ailments = emptyList(),
                        description = "A brutal elder dragon wreathed in flames.",
                        elements = listOf("fire"),
                        id = 45,
                        locations = listOf(Location(5, "Elder's Recess", 16)),
                        name = "Teostra",
                        resistances = emptyList(),
                        rewards = emptyList(),
                        species = "elder dragon",
                        type = "large",
                        weaknesses = listOf(Weaknesse(null, "water", 3))
                    ),
                    MonsterItem(
                        ailments = emptyList(),
                        description = "Apex predator of the Coral Highlands.",
                        elements = listOf("ice"),
                        id = 31,
                        locations = listOf(Location(2, "Coral Highlands", 15)),
                        name = "Legiana",
                        resistances = emptyList(),
                        rewards = emptyList(),
                        species = "flying wyvern",
                        type = "large",
                        weaknesses = listOf(Weaknesse(null, "thunder", 3))
                    )
                ),
                totalFiltrados = 4,
                favoritosIds = setOf(25, 45)
            ),
            onMonsterClick = {},
            onFavoritoClick = {},
            onCargarMas = {},
            onReintentar = {},
            onBusquedaChange = {},
            onFiltroChange = {}
        )
    }
}
