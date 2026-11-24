package com.example.practica_1_segundo_parcial.ui.simpsons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.practica_1_segundo_parcial.model.SimpsonsResponse
import com.example.practica_1_segundo_parcial.model.personajeDeLosSimpsons
import com.example.practica_1_segundo_parcial.ui.theme.Practica_1_Segundo_ParcialTheme

@Composable
fun SimpsonsScreen(
    modifier: Modifier = Modifier,
    viewModel: SimpsonsViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SimpsonsScreen(
        state = state,
        onLoadPage = { viewModel.load(it) },
        modifier = modifier
    )
}

@Composable
fun SimpsonsScreen(
    state: SimpsonsState,
    onLoadPage: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCharacterId by rememberSaveable { mutableStateOf<Int?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Personajes de Los Simpsons",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))

        when (val current = state) {
            SimpsonsState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is SimpsonsState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Error al cargar los personajes.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { onLoadPage(current.page) }) {
                        Text("Reintentar")
                    }
                }
            }

            is SimpsonsState.Content -> {
                val characters = current.response.results
                if (characters.isEmpty()) {
                    Text("No se encontraron personajes en esta página.")
                }

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(characters, key = { it.id }) { character ->
                        Card(
                            onClick = { selectedCharacterId = character.id },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = character.imageUrl,
                                    contentDescription = character.name,
                                    modifier = Modifier.size(80.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Column {
                                    Text(
                                        text = character.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    character.occupation?.let {
                                        Text(
                                            text = it,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    character.status?.let {
                                        Text(
                                            text = "Estado: $it",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = { onLoadPage(current.page - 1) },
                        enabled = current.page > 1 && current.response.prev != null
                    ) {
                        Text("Anterior")
                    }

                    Text("${current.page} / ${current.response.pages}")

                    TextButton(
                        onClick = { onLoadPage(current.page + 1) },
                        enabled = current.page < current.response.pages && current.response.next != null
                    ) {
                        Text("Siguiente")
                    }
                }

                selectedCharacterId?.let { id ->
                    characters.firstOrNull { it.id == id }?.let { character ->
                        AlertDialog(
                            onDismissRequest = { selectedCharacterId = null },
                            title = { Text(character.name) },
                            text = {
                                LazyColumn(
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    item {
                                        AsyncImage(
                                            model = character.imageUrl,
                                            contentDescription = character.name,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(200.dp),
                                            contentScale = ContentScale.Fit
                                        )
                                    }
                                    item {
                                        Text("Edad: ${character.age ?: "Desconocida"}")
                                        Text("Fecha de Nacimiento: ${character.birthdate ?: "Desconocida"}")
                                        Text("Género: ${character.gender ?: "Desconocido"}")
                                        Text("Ocupación: ${character.occupation ?: "Desconocida"}")
                                        Text("Estado: ${character.status ?: "Desconocido"}")
                                    }
                                    character.phrases?.takeIf { it.isNotEmpty() }?.let { phrases ->
                                        item {
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                "Frases célebres:",
                                                style = MaterialTheme.typography.titleSmall,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        items(phrases) { phrase ->
                                            Text(
                                                "• \"$phrase\"",
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    }
                                }
                            },
                            confirmButton = {
                                TextButton(onClick = { selectedCharacterId = null }) {
                                    Text("Cerrar")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

private val sampleSimpsonsCharacters = listOf(
    personajeDeLosSimpsons(
        id = 1,
        name = "Homer Simpson",
        age = 39,
        birthdate = "1956-05-12",
        gender = "Male",
        occupation = "Safety Inspector",
        phrases = listOf("Doh!", "Why you little...!"),
        portrait_path = "/character/1.webp",
        status = "Alive"
    ),
    personajeDeLosSimpsons(
        id = 2,
        name = "Marge Simpson",
        age = 39,
        birthdate = null,
        gender = "Female",
        occupation = "Unemployed",
        phrases = listOf("Hrmmm...", "Oh, Homie!"),
        portrait_path = "/character/2.webp",
        status = "Alive"
    )
)

private val sampleSimpsonsState = SimpsonsState.Content(
    page = 1,
    response = SimpsonsResponse(
        count = 1182,
        next = "https://thesimpsonsapi.com/api/characters?page=2",
        prev = null,
        pages = 60,
        results = sampleSimpsonsCharacters
    )
)

@Preview(showBackground = true)
@Composable
fun SimpsonsScreenPreview() {
    Practica_1_Segundo_ParcialTheme {
        SimpsonsScreen(
            state = sampleSimpsonsState,
            onLoadPage = {}
        )
    }
}
