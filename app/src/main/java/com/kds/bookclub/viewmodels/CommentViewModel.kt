package com.kds.bookclub.viewmodels
//handling comment thread for a specific book
//observes database updates and handles new comment posting
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.kds.bookclub.data.models.Comment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CommentViewModel(private val bookId: String) : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val commentsCollection = db.collection("comments").document(bookId).collection("threads")

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments

    init {
        //firestore listener for real time updates
        //data changes, _comments value updates
        Log.d("CommentViewModel", "Initialized for bookId: $bookId")
        commentsCollection.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.e("CommentViewModel", "Listen failed: $e")
                return@addSnapshotListener
            }
            val fetched = snapshot?.documents?.mapNotNull { it.toObject(Comment::class.java) } ?: emptyList()
            Log.d("CommentViewModel", "Fetched ${fetched.size} comments")
            _comments.value = fetched
        }
    }

    fun postComment(user: String, message: String) {
        //coroutine to write the new comment
        viewModelScope.launch {
            val comment = Comment(user = user, message = message)
            try {
                Log.d("CommentViewModel", "Posting comment: $comment")
                commentsCollection.add(comment).await()
                Log.d("CommentViewModel", "Comment posted successfully")
            } catch (e: Exception) {
                Log.e("CommentViewModel", "Error posting comment: ${e.message}", e)
            }
        }
    }
}