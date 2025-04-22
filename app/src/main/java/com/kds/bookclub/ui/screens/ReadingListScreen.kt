package com.kds.bookclub.ui.screens
//displays reading list from firestore
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kds.bookclub.data.models.toBook
import com.kds.bookclub.ui.components.BookCard
import com.kds.bookclub.viewmodels.ReadingListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingListScreen() {
    val vm: ReadingListViewModel = viewModel()
    val books by vm.books.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("Shared Reading List") }) }) {
        LazyColumn(contentPadding = it) {
            items(books) { book ->
                BookCard(book = book.toBook(), showAddButton = false)
            }
        }
    }
}
