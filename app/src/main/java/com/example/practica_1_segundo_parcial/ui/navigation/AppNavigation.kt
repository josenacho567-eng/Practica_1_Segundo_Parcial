package com.example.practica_1_segundo_parcial.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.practica_1_segundo_parcial.ui.detail.MonsterDetailScreen
import com.example.practica_1_segundo_parcial.ui.detail.MonsterDetailViewModel
import com.example.practica_1_segundo_parcial.ui.list.MonsterListScreen
import com.example.practica_1_segundo_parcial.ui.disney.DisneyScreen
import com.example.practica_1_segundo_parcial.ui.simpsons.SimpsonsScreen
import com.example.practica_1_segundo_parcial.feature.trivia.presentation.TriviaScreen

private const val RUTA_INICIO = "inicio"
private const val RUTA_LISTA = "lista"
private const val RUTA_DETALLE = "detalle"

/**
 * Grafo de navegacion:
 *  - "lista" (inicio) -> "detalle/{monsterId}"
 */
@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RUTA_INICIO,
        modifier = modifier
    ) {
        composable(RUTA_INICIO) {
            Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Elige una API")
                Button(onClick = { navController.navigate(RUTA_LISTA) }) { Text("Monster Hunter") }
                Button(onClick = { navController.navigate("disney") }) { Text("Disney") }
                Button(onClick = { navController.navigate("simpsons") }) { Text("Los Simpsons") }
                Button(onClick = { navController.navigate("trivia") }) { Text("Trivia") }
            }
        }
        composable("disney") { DisneyScreen() }
        composable("simpsons") { SimpsonsScreen() }
        composable("trivia") { TriviaScreen() }
        composable(RUTA_LISTA) {
            MonsterListScreen(
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
