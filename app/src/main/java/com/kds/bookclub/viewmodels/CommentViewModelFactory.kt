package com.kds.bookclub.viewmodels
//attempt to use bookId dependency
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CommentViewModelFactory(private val bookId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentViewModel(bookId) as T
    }
}