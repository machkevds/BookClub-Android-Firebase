package com.kds.bookclub.viewmodels
//handling comment thread for a specific book
//observes database updates and handles new comment posting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kds.bookclub.data.models.Comment
import com.kds.bookclub.data.repositories.CommentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CommentViewModel(private val bookId: String) : ViewModel() {

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments

    init {
        //firestore listener for real time updates
        //data changes, _comments value updates
        CommentRepository.observeComments(bookId) { updatedComments->
            _comments.value = updatedComments
        }
    }

    fun postComment(user: String, message: String) {
        //coroutine to write the new comment
        viewModelScope.launch {
            CommentRepository.addComment(bookId, user, message)
        }
    }
}
