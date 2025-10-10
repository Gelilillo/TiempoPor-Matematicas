package com.wezen.matesportiempo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.wezen.matesportiempo.data.Database.DatabaseHelper
import com.wezen.matesportiempo.screens.*


@Composable
fun MatesPorTiempoNavigation(dbHelper: DatabaseHelper) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onUserSelected = { userId ->
                    navController.navigate("main_menu/$userId")
                },
                dbHelper
            )
        }
        composable("main_menu/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
            MainMenuScreen(
                userId = userId,
                onNavigateToModule = { module ->
                    navController.navigate("$module/$userId")
                },
                onNavigateToConfig = {
                    navController.navigate("config/$userId")
                },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("config/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
            ConfigScreen(
                userId = userId,
                onBack = { navController.popBackStack() }
            )
        }
        composable("suma/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
            SumaScreen(
                userId = userId,
                onBack = { navController.popBackStack() }
            )
        }
        composable("Comprension/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
            ComprensionScreen(
                userId = userId,
                onBack = { navController.popBackStack() }
            )
        }
                /*
        composable("resta/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
            RestaScreen(
                userId = userId,
                onBack = { navController.popBackStack() }
            )
        }
        composable("multiplicacion/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
            MultiplicacionScreen(
                userId = userId,
                onBack = { navController.popBackStack() }
            )
        }
        composable("division/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
            DivisionScreen(
                userId = userId,
                onBack = { navController.popBackStack() }
            )
        }
        composable("tablas/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
            TablasScreen(
                userId = userId,
                onBack = { navController.popBackStack() }
            )
        }
        composable("comprension/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
            ComprensionScreen(
                userId = userId,
                onBack = { navController.popBackStack() }
            )
        }
        composable("examen/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
            ExamenScreen(
                userId = userId,
                onBack = { navController.popBackStack() }
            )
        }
        composable("estadisticas/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
            EstadisticasScreen(
                userId = userId,
                onBack = { navController.popBackStack() }
            )
        }*/
    }
}