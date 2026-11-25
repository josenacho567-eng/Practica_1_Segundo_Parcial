package com.example.practica_1_segundo_parcial.ui.simpsons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica_1_segundo_parcial.data.SimpsonsRepository
import com.example.practica_1_segundo_parcial.model.SimpsonsResponse
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface SimpsonsState {
    data object Loading : SimpsonsState
    data class Content(val page: Int, val response: SimpsonsResponse) : SimpsonsState
    data class Error(val page: Int) : SimpsonsState
}

class SimpsonsViewModel(
    private val repository: SimpsonsRepository = SimpsonsRepository.instance
) : ViewModel() {
    private val mutableState = MutableStateFlow<SimpsonsState>(SimpsonsState.Loading)
    val state = mutableState.asStateFlow()

    init {
        load(1)
    }

    fun load(page: Int) {
        mutableState.value = SimpsonsState.Loading
        viewModelScope.launch {
            mutableState.value = try {
                SimpsonsState.Content(page, repository.getCharacters(page))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                SimpsonsState.Error(page)
            }
        }
    }
}
