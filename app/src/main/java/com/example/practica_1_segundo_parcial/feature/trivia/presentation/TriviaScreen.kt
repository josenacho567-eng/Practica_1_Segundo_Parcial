package com.example.practica_1_segundo_parcial.feature.trivia.presentation

import android.text.Html
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriviaScreen(
    viewModel: TriviaViewModel = viewModel()
) {
    val state = viewModel.uiState

    Scaffold(
        topBar = { TopAppBar(title = { Text("Trivia API") }) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> CircularProgressIndicator()
                state.errorMessage != null -> ErrorContent(
                    message = state.errorMessage,
                    onRetry = viewModel::loadQuestions
                )
                else -> QuestionList(
                    state = state,
                    onReload = viewModel::loadQuestions
                )
            }
        }
    }
}

@Composable
private fun QuestionList(
    state: TriviaUiState,
    onReload: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(state.questions) { index, question ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Pregunta ${index + 1}",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = decodeHtml(question.question),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text("Categoría: ${decodeHtml(question.category)}")
                    Text("Dificultad: ${question.difficulty}")
                    Text(
                        text = "Respuesta: ${decodeHtml(question.correctAnswer)}",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        item {
            Button(
                onClick = onReload,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cargar nuevas preguntas")
            }
        }
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(message)
        Button(onClick = onRetry) {
            Text("Reintentar")
        }
    }
}

private fun decodeHtml(text: String): String {
    return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY).toString()
}
