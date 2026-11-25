package com.example.practica_1_segundo_parcial.ui.disney

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.practica_1_segundo_parcial.model.Data
import com.example.practica_1_segundo_parcial.model.Info
import com.example.practica_1_segundo_parcial.model.disneyCharacter
import com.example.practica_1_segundo_parcial.ui.theme.Practica_1_Segundo_ParcialTheme

@Composable
fun DisneyScreen(modifier: Modifier = Modifier, viewModel: DisneyViewModel = viewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    DisneyScreen(
        state = state,
        onLoadPage = { viewModel.load(it) },
        modifier = modifier
    )
}

@Composable
fun DisneyScreen(
    state: DisneyState,
    onLoadPage: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedId by rememberSaveable { mutableStateOf<Int?>(null) }
    Column(modifier.fillMaxSize().padding(16.dp)) {
        Text("Personajes de Disney", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))
        when (val current = state) {
            DisneyState.Loading -> CircularProgressIndicator()
            is DisneyState.Error -> {
                Text("No se pudo cargar el catálogo.")
                Button(onClick = { onLoadPage(current.page) }) { Text("Reintentar") }
            }
            is DisneyState.Content -> {
                val characters = current.response.data
                if (characters.isEmpty()) Text("No hay personajes en esta página.")
                LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(characters, key = { it._id }) { character ->
                        Card(onClick = { selectedId = character._id }, modifier = Modifier.fillMaxWidth()) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AsyncImage(character.imageUrl, null, Modifier.size(80.dp), contentScale = ContentScale.Crop)
                                Text(character.name, Modifier.padding(12.dp),style = MaterialTheme.typography.titleMedium)
                            }
                        }
                    }
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    TextButton({ onLoadPage(current.page - 1) }, enabled = current.page > 1) { Text("Anterior") }
                    Text("${current.page} / ${current.response.info.totalPages}")
                    TextButton({ onLoadPage(current.page + 1) }, enabled = current.response.info.nextPage != null) { Text("Siguiente") }
                }
                characters.firstOrNull { it._id == selectedId }?.let { character ->
                    AlertDialog(onDismissRequest = { selectedId = null }, title = { Text(character.name) },
                        text = {
                            LazyColumn (verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                item { AsyncImage(character.imageUrl, null, Modifier.fillMaxWidth().height(160.dp)) }
                                val groups = listOf("Películas" to character.films, "Series" to character.tvShows,
                                    "Cortos" to character.shortFilms, "Videojuegos" to character.videoGames,
                                    "Atracciones" to character.parkAttractions, "Aliados" to character.allies,
                                    "Enemigos" to character.enemies)
                                groups.filter { it.second.isNotEmpty() }.forEach { (title, values) ->
                                    item { Text(title, style = MaterialTheme.typography.titleMedium) }
                                    items(values) { Text(it) }
                                }
                            }
                        },
                        confirmButton = { TextButton({ selectedId = null }) { Text("Cerrar") } })
                }
            }
        }
    }
}

private val sampleCharacterList = listOf(
    Data(
        _id = 112,
        allies = listOf("Pluto", "Donald Duck"),
        enemies = listOf("Pete"),
        films = listOf("Steamboat Willie", "Fantasia"),
        imageUrl = "https://static.wikia.nocookie.net/disney/images/7/7f/Mickey_Mouse_Pose_02.png",
        name = "Mickey Mouse",
        parkAttractions = listOf("Mickey & Minnie's Runaway Railway"),
        shortFilms = listOf("Get a Horse!"),
        tvShows = listOf("Mickey Mouse Clubhouse"),
        url = "https://api.disneyapi.dev/characters/112",
        videoGames = listOf("Kingdom Hearts", "Epic Mickey")
    ),
    Data(
        _id = 113,
        allies = listOf("Mickey Mouse"),
        enemies = listOf("Pete"),
        films = listOf("The Wise Little Hen"),
        imageUrl = "https://static.wikia.nocookie.net/disney/images/6/6b/Donald_Duck_pose.png",
        name = "Donald Duck",
        parkAttractions = listOf("Mickey's PhilharMagic"),
        shortFilms = listOf("Der Fuehrer's Face"),
        tvShows = listOf("DuckTales"),
        url = "https://api.disneyapi.dev/characters/113",
        videoGames = listOf("Kingdom Hearts")
    )
)

private val sampleDisneyState = DisneyState.Content(
    page = 1,
    response = disneyCharacter(
        data = sampleCharacterList,
        info = Info(
            count = 2,
            nextPage = "https://api.disneyapi.dev/character?page=2&pageSize=50",
            previousPage = null,
            totalPages = 149
        )
    )
)

@Preview(showBackground = true)
@Composable
fun DisneyScreenPreview() {
    Practica_1_Segundo_ParcialTheme {
        DisneyScreen(
            state = sampleDisneyState,
            onLoadPage = {}
        )
    }
}
