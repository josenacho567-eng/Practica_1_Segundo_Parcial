package com.example.practica_1_segundo_parcial.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.practica_1_segundo_parcial.data.FavoritosRepository
import com.example.practica_1_segundo_parcial.data.MonsterRepository
import com.example.practica_1_segundo_parcial.data.local.MonsterLocalStorage
import com.example.practica_1_segundo_parcial.modelMonster.MonsterItem
import com.example.practica_1_segundo_parcial.util.Resultado
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MonsterDetailUiState(
    val detalle: MonsterItem? = null,
    val cargando: Boolean = true,
    val error: String? = null,
    val esFavorito: Boolean = false
)

/**
 * ViewModel del detalle de un monstruo. Recibe `monsterId` via [SavedStateHandle].
 */
class MonsterDetailViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val localStorage = MonsterLocalStorage.obtener(application)
    private val repositorio = MonsterRepository(localStorage)
    private val favoritosRepositorio = FavoritosRepository(localStorage)

    private val monsterId: Int = checkNotNull(savedStateHandle.get<Int>(ARG_MONSTER_ID))

    private val _estadoApi = MutableStateFlow(MonsterDetailUiState())

    val uiState: StateFlow<MonsterDetailUiState> = combine(
        _estadoApi,
        favoritosRepositorio.observarIds()
    ) { estadoApi, ids ->
        estadoApi.copy(esFavorito = monsterId in ids)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MonsterDetailUiState()
    )

    init {
        cargar()
    }

    fun cargar() {
        _estadoApi.update { it.copy(cargando = true, error = null) }
        viewModelScope.launch {
            when (val resultado = repositorio.obtenerMonstruo(monsterId)) {
                is Resultado.Exito -> _estadoApi.update {
                    it.copy(detalle = resultado.datos, cargando = false, error = null)
                }
                is Resultado.Error -> _estadoApi.update {
                    it.copy(cargando = false, error = resultado.mensaje)
                }
            }
        }
    }

    fun alternarFavorito() {
        val esFavorito = uiState.value.esFavorito
        if (esFavorito) {
            favoritosRepositorio.quitar(monsterId)
        } else {
            favoritosRepositorio.agregar(monsterId)
        }
    }

    companion object {
        const val ARG_MONSTER_ID = "monsterId"
    }
}
