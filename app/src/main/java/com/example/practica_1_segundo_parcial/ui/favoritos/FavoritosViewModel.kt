package com.example.practica_1_segundo_parcial.ui.favoritos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica_1_segundo_parcial.data.FavoritosRepository
import com.example.practica_1_segundo_parcial.data.local.MonsterLocalStorage
import com.example.practica_1_segundo_parcial.modelMonster.MonsterItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class FavoritosUiState(
    val favoritos: List<MonsterItem> = emptyList(),
    val cargando: Boolean = true
)

/**
 * ViewModel de la pantalla de favoritos. Lee desde almacenamiento local para funcionar sin internet.
 */
class FavoritosViewModel(application: Application) : AndroidViewModel(application) {

    private val favoritosRepositorio =
        FavoritosRepository(MonsterLocalStorage.obtener(application))

    val uiState: StateFlow<FavoritosUiState> = favoritosRepositorio.observarFavoritos()
        .map { lista -> FavoritosUiState(favoritos = lista, cargando = false) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FavoritosUiState()
        )

    fun quitar(monstruo: MonsterItem) {
        favoritosRepositorio.quitar(monstruo.id)
    }
}
