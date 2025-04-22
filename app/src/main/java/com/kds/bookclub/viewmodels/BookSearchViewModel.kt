package com.kds.bookclub.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kds.bookclub.api.GoogleBooksApi
import com.kds.bookclub.data.models.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookSearchViewModel : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun search(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _books.value = try {
                GoogleBooksApi.searchBooks(query)
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
            _isLoading.value = false
        }
    }
}
