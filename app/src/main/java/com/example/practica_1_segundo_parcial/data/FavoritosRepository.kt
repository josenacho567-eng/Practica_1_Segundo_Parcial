package com.example.practica_1_segundo_parcial.data

import com.example.practica_1_segundo_parcial.data.local.MonsterLocalStorage
import com.example.practica_1_segundo_parcial.modelMonster.MonsterItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

/**
 * Repositorio local de favoritos. Expone los IDs favoritos y la lista filtrada de monstruos
 * para que los ViewModels trabajen siempre con [MonsterItem] y Flow reactivos.
 */
class FavoritosRepository(private val localStorage: MonsterLocalStorage) {

    fun observarFavoritos(): Flow<List<MonsterItem>> = combine(
        localStorage.observarMonstruos(),
        localStorage.observarFavoritosIds()
    ) { monstruos, ids ->
        monstruos.filter { it.id in ids }
    }

    fun observarIds(): Flow<Set<Int>> = localStorage.observarFavoritosIds()

    fun agregar(id: Int) = localStorage.agregarFavorito(id)

    fun quitar(id: Int) = localStorage.quitarFavorito(id)
}
