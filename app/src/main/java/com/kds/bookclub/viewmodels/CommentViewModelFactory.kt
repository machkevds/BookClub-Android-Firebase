package com.kds.bookclub.viewmodels
//attempt to use bookId dependency
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CommentViewModelFactory(private val bookId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CommentViewModel(bookId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
