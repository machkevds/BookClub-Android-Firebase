package com.kds.bookclub.ui.screens
//displays search bar and book list, book detail navigation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kds.bookclub.data.models.Book
import com.kds.bookclub.navigation.Routes
import com.kds.bookclub.ui.components.BookCard
import com.kds.bookclub.viewmodels.BookSearchViewModel

@Composable
fun BookSearchScreen(navController: NavController) {
    val vm: BookSearchViewModel = viewModel()
    val books by vm.books.collectAsState()
    val isLoading by vm.isLoading.collectAsState()

    var query by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search for books") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { vm.search(query) }) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(books) { book ->
                    BookCard(
                        book = book,
                        onClick = {
                            //attempt to pass the book via SavedStateHandle
                            navController.currentBackStackEntry?.savedStateHandle?.set("book", book)
                            navController.navigate("${Routes.BOOK_DETAIL}/${book.id}")
                        }
                    )
                }
            }
        }
    }
}
