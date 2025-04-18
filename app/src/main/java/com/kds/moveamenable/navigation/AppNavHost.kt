package com.kds.moveamenable.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kds.moveamenable.screens.BrowseScreen
import com.kds.moveamenable.screens.FocusScreen
import com.kds.moveamenable.screens.InformationScreen
import com.kds.moveamenable.screens.MainScreen
import com.kds.moveamenable.screens.MyWorkoutScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.MAIN_SCREEN
    ) {
        composable(Routes.MAIN_SCREEN) {
            MainScreen(navController)
        }
        composable(Routes.MY_WORKOUTS) {
            MyWorkoutScreen(navController)
        }
        composable(Routes.INFORMATION) {
            InformationScreen(navController)
        }
        composable(Routes.FOCUS_TOOLS) {
            FocusScreen(navController)
        }
    }
}