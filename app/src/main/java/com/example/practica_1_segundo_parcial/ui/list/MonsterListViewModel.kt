package com.example.practica_1_segundo_parcial.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica_1_segundo_parcial.data.MonsterRepository
import com.example.practica_1_segundo_parcial.modelMonster.MonsterItem
import com.example.practica_1_segundo_parcial.util.Resultado
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class FiltroTipoMonstruo(val etiqueta: String) {
    TODOS("Todos"),
    GRANDES("Grandes"),
    PEQUENOS("Pequeños")
}

/** Estado completo e inmutable de la pantalla de lista de monstruos. */
data class MonsterListUiState(
    val monstruos: List<MonsterItem> = emptyList(),
    val totalFiltrados: Int = 0,
    val cargando: Boolean = false,
    val error: String? = null,
    val hayMas: Boolean = false,
    val busqueda: String = "",
    val filtroTipo: FiltroTipoMonstruo = FiltroTipoMonstruo.TODOS
)

private data class EstadoRed(
    val listaCompleta: List<MonsterItem> = emptyList(),
    val cargando: Boolean = true,
    val error: String? = null
)

private data class EstadoFiltros(
    val busqueda: String = "",
    val filtroTipo: FiltroTipoMonstruo = FiltroTipoMonstruo.TODOS,
    val limiteMostrado: Int = 20
)

/**
 * ViewModel de la lista de monstruos (patron MVVM + StateFlow) usando unicamente el repositorio remoto.
 */
class MonsterListViewModel(
    private val repositorio: MonsterRepository = MonsterRepository.instance
) : ViewModel() {

    private val _estadoRed = MutableStateFlow(EstadoRed())
    private val _filtros = MutableStateFlow(EstadoFiltros())

    val uiState: StateFlow<MonsterListUiState> = combine(
        _estadoRed,
        _filtros
    ) { red, filtros ->
        val filtrados = red.listaCompleta.filter { monstruo ->
            val coincideTipo = when (filtros.filtroTipo) {
                FiltroTipoMonstruo.TODOS -> true
                FiltroTipoMonstruo.GRANDES -> monstruo.type.equals("large", ignoreCase = true)
                FiltroTipoMonstruo.PEQUENOS -> monstruo.type.equals("small", ignoreCase = true)
            }
            val consulta = filtros.busqueda.trim()
            val coincideBusqueda = consulta.isEmpty() ||
                monstruo.name.contains(consulta, ignoreCase = true) ||
                monstruo.species.contains(consulta, ignoreCase = true)
            coincideTipo && coincideBusqueda
        }
        val visibles = filtrados.take(filtros.limiteMostrado)

        MonsterListUiState(
            monstruos = visibles,
            totalFiltrados = filtrados.size,
            cargando = red.cargando,
            error = red.error,
            hayMas = visibles.size < filtrados.size,
            busqueda = filtros.busqueda,
            filtroTipo = filtros.filtroTipo
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MonsterListUiState(cargando = true)
    )

    init {
        recargar()
    }

    /** Descarga la lista completa desde https://mhw-db.com/monsters (tambien sirve como Reintentar). */
    fun recargar() {
        _estadoRed.update { it.copy(cargando = true, error = null) }
        viewModelScope.launch {
            when (val resultado = repositorio.obtenerMonstruos()) {
                is Resultado.Exito -> _estadoRed.update {
                    it.copy(listaCompleta = resultado.datos, cargando = false, error = null)
                }
                is Resultado.Error -> _estadoRed.update {
                    it.copy(cargando = false, error = resultado.mensaje)
                }
            }
        }
    }

    fun cargarMas() {
        _filtros.update { it.copy(limiteMostrado = it.limiteMostrado + TAMANO_PAGINA) }
    }

    fun actualizarBusqueda(texto: String) {
        _filtros.update { it.copy(busqueda = texto, limiteMostrado = TAMANO_PAGINA) }
    }

    fun seleccionarFiltro(filtro: FiltroTipoMonstruo) {
        _filtros.update { it.copy(filtroTipo = filtro, limiteMostrado = TAMANO_PAGINA) }
    }

    private companion object {
        const val TAMANO_PAGINA = 20
    }
}
