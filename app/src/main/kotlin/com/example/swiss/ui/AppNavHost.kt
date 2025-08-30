package com.example.swiss.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(onNewTournament = { navController.navigate("new") }, onHistory = { navController.navigate("history") }) }
        composable("new") { NewTournamentScreen(onDone = { tid -> navController.navigate("t/$tid") { popUpTo("home") } }) }
        composable("history") { HistoryScreen(onOpen = { id -> navController.navigate("t/$id") }) }
        composable("t/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            TournamentScreen(id, onManualPair = { navController.navigate("t/$id/pair") })
        }
        composable("t/{id}/pair") { ManualPairingScreen(onDone = { navController.popBackStack() }) }
    }
}
