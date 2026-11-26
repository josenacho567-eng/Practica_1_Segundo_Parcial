package com.example.practica_1_segundo_parcial.data

import com.example.practica_1_segundo_parcial.data.mapper.toDomain
import com.example.practica_1_segundo_parcial.data.remote.MonsterApiService
import com.example.practica_1_segundo_parcial.data.remote.RetrofitClient
import com.example.practica_1_segundo_parcial.modelMonster.MonsterItem
import com.example.practica_1_segundo_parcial.util.Resultado
import java.io.IOException
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException

/**
 * Capa de datos (Modelo en MVVM) para los monstruos de Monster Hunter World.
 * Consume la API remota mediante DTOs y los transforma al modelo de dominio con mapeadores.
 */
class MonsterRepository(
    private val api: MonsterApiService = RetrofitClient.monsterApi
) {

    /** Descarga la lista de monstruos desde https://mhw-db.com/monsters. */
    suspend fun obtenerMonstruos(): Resultado<List<MonsterItem>> = llamarApi {
        api.obtenerMonstruos()
            .map { it.toDomain() }
            .sortedBy { it.id }
    }

    /** Descarga el detalle de un monstruo desde https://mhw-db.com/monsters/{id}. */
    suspend fun obtenerMonstruo(id: Int): Resultado<MonsterItem> = llamarApi {
        api.obtenerMonstruo(id).toDomain()
    }

    private suspend fun <T> llamarApi(llamada: suspend () -> T): Resultado<T> =
        try {
            Resultado.Exito(llamada())
        } catch (e: CancellationException) {
            throw e
        } catch (e: HttpException) {
            Resultado.Error("El servidor respondió con un error (código ${e.code()}).")
        } catch (e: IOException) {
            Resultado.Error("No hay conexión a internet. Revisa tu red e inténtalo de nuevo.")
        } catch (e: Exception) {
            Resultado.Error("Ocurrió un error inesperado: ${e.localizedMessage ?: "desconocido"}")
        }

    companion object {
        val instance by lazy { MonsterRepository() }
    }
}
