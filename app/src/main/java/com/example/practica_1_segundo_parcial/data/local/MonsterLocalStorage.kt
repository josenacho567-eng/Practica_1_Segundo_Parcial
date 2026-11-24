package com.example.practica_1_segundo_parcial.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.practica_1_segundo_parcial.modelMonster.MonsterItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Persistencia local ligera (cache de monstruos + favoritos) usando SharedPreferences y Gson.
 * Mantiene StateFlows reactivos para que los ViewModels observen cambios al instante.
 */
class MonsterLocalStorage private constructor(context: Context) {

    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    private val _monstruos = MutableStateFlow(leerMonstruos())
    private val _favoritosIds = MutableStateFlow(leerFavoritosIds())

    fun observarMonstruos(): Flow<List<MonsterItem>> = _monstruos.asStateFlow()

    fun observarFavoritosIds(): Flow<Set<Int>> = _favoritosIds.asStateFlow()

    fun hayMonstruosGuardados(): Boolean = _monstruos.value.isNotEmpty()

    fun buscarMonstruoPorId(id: Int): MonsterItem? = _monstruos.value.firstOrNull { it.id == id }

    fun guardarMonstruos(monstruos: List<MonsterItem>) {
        val ordenados = monstruos.sortedBy { it.id }
        prefs.edit().putString(KEY_MONSTRUOS_CACHE, gson.toJson(ordenados)).apply()
        _monstruos.value = ordenados
    }

    fun agregarFavorito(id: Int) {
        val actualizados = _favoritosIds.value + id
        guardarFavoritosIds(actualizados)
    }

    fun quitarFavorito(id: Int) {
        val actualizados = _favoritosIds.value - id
        guardarFavoritosIds(actualizados)
    }

    private fun guardarFavoritosIds(ids: Set<Int>) {
        prefs.edit()
            .putStringSet(KEY_FAVORITOS_IDS, ids.map { it.toString() }.toSet())
            .apply()
        _favoritosIds.value = ids
    }

    private fun leerMonstruos(): List<MonsterItem> {
        val json = prefs.getString(KEY_MONSTRUOS_CACHE, null) ?: return emptyList()
        return try {
            val type = object : TypeToken<List<MonsterItem>>() {}.type
            gson.fromJson<List<MonsterItem>>(json, type) ?: emptyList()
        } catch (_: Exception) {
            emptyList()
        }
    }

    private fun leerFavoritosIds(): Set<Int> {
        val guardados = prefs.getStringSet(KEY_FAVORITOS_IDS, emptySet()) ?: emptySet()
        return guardados.mapNotNull { it.toIntOrNull() }.toSet()
    }

    companion object {
        private const val PREFS_NAME = "mhw_bestiary_prefs"
        private const val KEY_MONSTRUOS_CACHE = "monstruos_cache_json"
        private const val KEY_FAVORITOS_IDS = "favoritos_ids"

        @Volatile
        private var instancia: MonsterLocalStorage? = null

        fun obtener(context: Context): MonsterLocalStorage =
            instancia ?: synchronized(this) {
                instancia ?: MonsterLocalStorage(context).also { instancia = it }
            }
    }
}
