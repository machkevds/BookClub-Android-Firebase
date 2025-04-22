package com.kds.bookclub.ui.components
//represents a single comment in the comment list
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kds.bookclub.data.models.Comment
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CommentItem(comment: Comment) {
    val date = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()).format(Date(comment.timestamp))
    Card(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("${comment.user} • $date", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(comment.message, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
