package com.kds.bookclub.navigation
//replaces nav_graph
// navigation routes between different app screens
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kds.bookclub.data.models.Book
import com.kds.bookclub.ui.screens.*
import com.kds.bookclub.auth.AuthViewModel
import com.kds.bookclub.auth.SignInScreen
import com.kds.bookclub.viewmodels.BookSearchViewModel
import com.kds.bookclub.viewmodels.ReadingListViewModel

@Composable
fun AppNavHost(navController: NavHostController) {
    val authViewModel: AuthViewModel = viewModel()
    val isSignedIn by authViewModel.isSignedIn.collectAsState()

    NavHost(
        navController = navController,
        startDestination = if (isSignedIn) Routes.MAIN else Routes.SIGN_IN
    ) {
        composable(Routes.SIGN_IN) {
            SignInScreen(onSignedIn = {
                navController.navigate(Routes.MAIN) {
                    popUpTo(0) //clear stack after sign in
                }
            })
        }
        composable(Routes.MAIN) {
            MainScreen(navController)
        }
        composable(Routes.SEARCH) {
            BookSearchScreen(navController)
        }
        composable(Routes.READING_LIST) {
            ReadingListScreen(navController)
        }
        composable(Routes.BOOK_DETAIL) {
            val backStackEntry = navController.previousBackStackEntry
            val book = backStackEntry?.savedStateHandle?.get<Book>("book")
            if (book != null) {
                BookDetailScreen(navController, book)
            }
        }
    }
}
