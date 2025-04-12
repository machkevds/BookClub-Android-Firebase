package com.kds.moveamenable.ui.navigation
// Centralized route definitions that replace traditional Fragment navigation graphs.

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kds.moveamenable.ui.screens.FocusScreen
import com.kds.moveamenable.ui.screens.InfoScreen
import com.kds.moveamenable.ui.screens.MainScreen
import com.kds.moveamenable.ui.screens.WorkoutScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(
                onWorkoutClick = { navController.navigate(Screen.Workout.route) },
                onFocusClick = { navController.navigate(Screen.Focus.route) },
                onInfoClick = { navController.navigate(Screen.Info.route) }
            )
        }

        composable(Screen.Workout.route) { WorkoutScreen { navController.popBackStack() } }
        composable(Screen.Focus.route) { FocusScreen { navController.popBackStack() } }
        composable(Screen.Info.route) { InfoScreen { navController.popBackStack() } }
    }
}