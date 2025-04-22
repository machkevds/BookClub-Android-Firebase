package com.kds.bookclub.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kds.bookclub.data.models.Book
import com.kds.bookclub.data.models.ReadingListEntry
import com.kds.bookclub.ui.components.BookCard
import com.kds.bookclub.ui.components.FancyBackButton
import com.kds.bookclub.viewmodels.ReadingListViewModel
import com.kds.bookclub.navigation.Routes

@Composable
fun ReadingListScreen(navController: NavController, vm: ReadingListViewModel = viewModel()) {
    val books by vm.books.collectAsState()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        FancyBackButton(navController, destination = Routes.MAIN)

        if (books.isEmpty()) {
            Text("No books in the reading list yet.", style = MaterialTheme.typography.bodyMedium)
        } else {
            LazyColumn {
                items(books) { book ->
                    BookCard(book = book.toBook()) {
                        navController.currentBackStackEntry?.savedStateHandle?.set("book", book.toBook())
                        navController.navigate(Routes.BOOK_DETAIL)
                    }
                }
            }
        }
    }
}

fun ReadingListEntry.toBook(): Book {
    return Book(
        id = this.id,
        title = this.title,
        authors = this.authors,
        thumbnail = "",            //placeholder
        description = ""
    )
}
