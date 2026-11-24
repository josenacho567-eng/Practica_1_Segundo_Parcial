package com.example.practica_1_segundo_parcial.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica_1_segundo_parcial.data.MonsterRepository
import com.example.practica_1_segundo_parcial.modelMonster.MonsterItem
import com.example.practica_1_segundo_parcial.util.Resultado
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MonsterDetailUiState(
    val detalle: MonsterItem? = null,
    val cargando: Boolean = true,
    val error: String? = null
)

/**
 * ViewModel del detalle de un monstruo. Recibe `monsterId` via [SavedStateHandle]
 * y consulta directamente el repositorio remoto.
 */
class MonsterDetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val repositorio: MonsterRepository = MonsterRepository.instance
    private val monsterId: Int = checkNotNull(savedStateHandle.get<Int>(ARG_MONSTER_ID))

    private val _uiState = MutableStateFlow(MonsterDetailUiState())
    val uiState: StateFlow<MonsterDetailUiState> = _uiState.asStateFlow()

    init {
        cargar()
    }

    fun cargar() {
        _uiState.update { it.copy(cargando = true, error = null) }
        viewModelScope.launch {
            when (val resultado = repositorio.obtenerMonstruo(monsterId)) {
                is Resultado.Exito -> _uiState.update {
                    it.copy(detalle = resultado.datos, cargando = false, error = null)
                }
                is Resultado.Error -> _uiState.update {
                    it.copy(cargando = false, error = resultado.mensaje)
                }
            }
        }
    }

    companion object {
        const val ARG_MONSTER_ID = "monsterId"
    }
}
