package com.kds.moveamenable.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kds.moveamenable.auth.AuthViewModel
import com.kds.moveamenable.auth.SignInScreen
// todo import com.kds.moveamenable.ui.screens.FeedScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    val authViewModel: AuthViewModel = viewModel()

    NavHost(navController, startDestination = if (authViewModel.isSignedIn.value) Routes.FEED else Routes.SIGN_IN) {
        composable(Routes.SIGN_IN) {
            SignInScreen(onSignedIn = { navController.navigate(Routes.FEED) })
        }
        composable(Routes.FEED) {
            // todo FeedScreen() // Stub, will be built in Stage 3
        }
    }
}
