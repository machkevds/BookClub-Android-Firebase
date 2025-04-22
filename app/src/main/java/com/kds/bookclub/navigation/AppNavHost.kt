package com.kds.bookclub.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kds.bookclub.auth.AuthViewModel
import com.kds.bookclub.auth.SignInScreen
import com.kds.bookclub.ui.screens.BookSearchScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    val authViewModel: AuthViewModel = viewModel()
    val isSignedIn by authViewModel.isSignedIn.collectAsState()

    NavHost(
        navController = navController,
        startDestination = if (isSignedIn) Routes.SEARCH else Routes.SIGN_IN
    ) {
        composable(Routes.SIGN_IN) {
            SignInScreen(onSignedIn = {
                navController.navigate(Routes.SEARCH) {
                    popUpTo(Routes.SIGN_IN) { inclusive = true }
                }
            })
        }
        composable(Routes.SEARCH) {
            BookSearchScreen() // Placeholder, added in Stage 2
        }
    }
}
