package com.kds.bookclub.ui.screens
// shows details for a selected book, shows comment thread
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
import com.google.firebase.auth.FirebaseAuth
import com.kds.bookclub.data.models.Book
import com.kds.bookclub.ui.components.CommentItem
import com.kds.bookclub.ui.components.FancyBackButton
import com.kds.bookclub.ui.theme.PrimaryButtonDefaults
import com.kds.bookclub.ui.theme.elevatedShadow
import com.kds.bookclub.viewmodels.CommentViewModel
import com.kds.bookclub.viewmodels.CommentViewModelFactory

@Composable
fun BookDetailScreen(navController: NavController?, book: Book) {
    val safeNavController = navController ?: return

    val vm: CommentViewModel = viewModel(factory = CommentViewModelFactory(book.id))
    val comments by vm.comments.collectAsState()
    var message by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        FancyBackButton(safeNavController)

        Text(book.title, style = MaterialTheme.typography.headlineMedium)
        Text("by ${book.authors.joinToString(", ")}", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Leave a comment") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                val user = FirebaseAuth.getInstance().currentUser?.displayName ?: "Anonymous"
                //comment posted to firestore
                vm.postComment(user, message)
                //clears input box after posting

                message = ""
            },
            colors = PrimaryButtonDefaults(),
            shape = RoundedCornerShape(50),
            modifier = Modifier.fillMaxWidth().elevatedShadow()
        ) {
            Text("Post")
        }

        Spacer(modifier = Modifier.height(16.dp))
        //displays comments in a list, observing comment view model
        LazyColumn {
            items(comments) { comment ->
                CommentItem(comment)
            }
        }
    }
}
