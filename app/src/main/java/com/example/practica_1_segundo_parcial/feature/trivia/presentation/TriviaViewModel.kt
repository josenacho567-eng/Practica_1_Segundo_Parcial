package com.example.practica_1_segundo_parcial.feature.trivia.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica_1_segundo_parcial.feature.trivia.data.remote.TriviaApiClient
import com.example.practica_1_segundo_parcial.feature.trivia.data.repository.TriviaRepository
import kotlinx.coroutines.launch

class TriviaViewModel : ViewModel() {
    private val repository = TriviaRepository(TriviaApiClient.service)

    var uiState by mutableStateOf(TriviaUiState())
        private set

    init {
        loadQuestions()
    }

    fun loadQuestions() {
        viewModelScope.launch {
            uiState = TriviaUiState(isLoading = true)

            uiState = try {
                TriviaUiState(questions = repository.getQuestions())
            } catch (exception: Exception) {
                TriviaUiState(
                    errorMessage = exception.message
                        ?: "No se pudieron cargar las preguntas"
                )
            }
        }
    }
}
