package com.kds.bookclub.navigation
//replaces nav_graph
// navigation routes between different app screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kds.bookclub.auth.AuthViewModel
import com.kds.bookclub.auth.SignInScreen
import com.kds.bookclub.data.models.Book
import com.kds.bookclub.ui.screens.BookDetailScreen
import com.kds.bookclub.ui.screens.BookSearchScreen
import com.kds.bookclub.ui.screens.ReadingListScreen

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
            BookSearchScreen(navController)
        }
        composable(Routes.READING_LIST) {
            ReadingListScreen()
        }

        composable("${Routes.BOOK_DETAIL}/{bookId}") { backStackEntry ->
            val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
            val book = savedStateHandle?.get<Book>("book")

            if (book != null) {
                BookDetailScreen(book)
            } else {
                Text("Book not found")
            }
        }


    }
}
