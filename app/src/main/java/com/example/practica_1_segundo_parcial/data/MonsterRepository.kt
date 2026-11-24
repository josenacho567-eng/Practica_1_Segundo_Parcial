package com.example.practica_1_segundo_parcial.data

import com.example.practica_1_segundo_parcial.data.local.MonsterLocalStorage
import com.example.practica_1_segundo_parcial.data.remote.MonsterApiService
import com.example.practica_1_segundo_parcial.data.remote.RetrofitClient
import com.example.practica_1_segundo_parcial.modelMonster.MonsterItem
import com.example.practica_1_segundo_parcial.util.Resultado
import java.io.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

/**
 * Capa de datos (Modelo en MVVM) para los monstruos de Monster Hunter World.
 * Coordina la API de MHW-DB (Retrofit) y la cache local para soporte offline.
 */
class MonsterRepository(
    private val localStorage: MonsterLocalStorage,
    private val api: MonsterApiService = RetrofitClient.monsterApi
) {

    fun observarMonstruos(): Flow<List<MonsterItem>> = localStorage.observarMonstruos()

    fun hayMonstruosGuardados(): Boolean = localStorage.hayMonstruosGuardados()

    /** Descarga todos los monstruos desde https://mhw-db.com/monsters y los guarda en cache. */
    suspend fun cargarMonstruos(): Resultado<Int> = llamarApi {
        val monstruos = api.obtenerMonstruos()
        localStorage.guardarMonstruos(monstruos)
        monstruos.size
    }

    /**
     * Obtiene el detalle de un monstruo por ID. Si existe en cache local lo retorna al instante;
     * si no esta en cache, lo consulta en https://mhw-db.com/monsters/{id}.
     */
    suspend fun obtenerMonstruo(id: Int): Resultado<MonsterItem> {
        localStorage.buscarMonstruoPorId(id)?.let { enCache ->
            return Resultado.Exito(enCache)
        }
        return llamarApi { api.obtenerMonstruo(id) }
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
}
