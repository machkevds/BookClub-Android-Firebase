package com.kds.bookclub.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.kds.bookclub.data.models.Book

@Composable
fun BookCard(book: Book) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(Modifier.padding(12.dp)) {
            book.thumbnail?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = book.title,
                    modifier = Modifier
                        .width(80.dp)
                        .height(120.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
            }

            Column {
                Text(text = book.title, style = MaterialTheme.typography.titleMedium)
                Text(text = "by ${book.authors.joinToString(", ")}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
