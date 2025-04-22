package com.kds.bookclub.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kds.bookclub.ui.components.BookCard
import com.kds.bookclub.ui.components.FancyBackButton
import com.kds.bookclub.viewmodels.BookSearchViewModel
import com.kds.bookclub.navigation.Routes
import com.kds.bookclub.ui.theme.PrimaryButtonDefaults
import com.kds.bookclub.ui.theme.elevatedShadow

@Composable
fun BookSearchScreen(navController: NavController, vm: BookSearchViewModel = viewModel()) {
    val books by vm.books.collectAsState()
    var query by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        FancyBackButton(navController, destination = Routes.MAIN)

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search books") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { vm.search(query) },
            colors = PrimaryButtonDefaults(),
            shape = RoundedCornerShape(50),
            modifier = Modifier.fillMaxWidth().elevatedShadow()
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(books) { book ->
                BookCard(book = book) {
                    navController.currentBackStackEntry?.savedStateHandle?.set("book", book)
                    navController.navigate(Routes.BOOK_DETAIL)
                }
            }
        }
    }
}
