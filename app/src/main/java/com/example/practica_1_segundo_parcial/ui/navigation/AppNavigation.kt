package com.example.practica_1_segundo_parcial.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.practica_1_segundo_parcial.ui.detail.MonsterDetailScreen
import com.example.practica_1_segundo_parcial.ui.detail.MonsterDetailViewModel
import com.example.practica_1_segundo_parcial.ui.favoritos.FavoritosScreen
import com.example.practica_1_segundo_parcial.ui.list.MonsterListScreen

private const val RUTA_LISTA = "lista"
private const val RUTA_DETALLE = "detalle"
private const val RUTA_FAVORITOS = "favoritos"

/**
 * Grafo de navegacion (equivalente a PokeApiEjemplo):
 *  - "lista" (inicio) -> "detalle/{monsterId}" o "favoritos"
 *  - "favoritos" -> "detalle/{monsterId}"
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = RUTA_LISTA) {
        composable(RUTA_LISTA) {
            MonsterListScreen(
                onMonsterClick = { id -> navController.navigate("$RUTA_DETALLE/$id") },
                onVerFavoritos = { navController.navigate(RUTA_FAVORITOS) }
            )
        }
        composable(RUTA_FAVORITOS) {
            FavoritosScreen(
                onVolver = { navController.popBackStack() },
                onMonsterClick = { id -> navController.navigate("$RUTA_DETALLE/$id") }
            )
        }
        composable(
            route = "$RUTA_DETALLE/{${MonsterDetailViewModel.ARG_MONSTER_ID}}",
            arguments = listOf(
                navArgument(MonsterDetailViewModel.ARG_MONSTER_ID) { type = NavType.IntType }
            )
        ) {
            MonsterDetailScreen(onVolver = { navController.popBackStack() })
        }
    }
}
