package com.kds.bookclub.viewmodels
// book search logic
//supposed to handle api calls, UI search state
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kds.bookclub.api.GoogleBooksApi
import com.kds.bookclub.data.models.Book
import com.kds.bookclub.data.repositories.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookSearchViewModel : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var lastSearchTime = 0L

    fun search(query: String) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastSearchTime < 2000) {
            println("[DEBUG] Search throttled to prevent spamming")
            return
        }
        lastSearchTime = currentTime

        viewModelScope.launch {
            _isLoading.value = true
            _books.value = try {
                val results = GoogleBooksApi.searchBooks(query)
                if (results.isEmpty()) {
                    println("[DEBUG] Using MOCK DATA fallback due to API failure")
                    getMockBooks(query)
                } else {
                    results
                }
            } catch (e: Exception) {
                e.printStackTrace()
                println("[DEBUG] Using MOCK DATA fallback due to exception")
                getMockBooks(query)
            }
            _isLoading.value = false
        }
    }

    private fun getMockBooks(query: String): List<Book> {
        return listOf(
            Book("mock1", "$query - Mock Book One", listOf("Author A"), null, "Description A"),
            Book("mock2", "$query - Mock Book Two", listOf("Author B"), null, "Description B"),
            Book("mock3", "$query - Mock Book Three", listOf("Author C"), null, "Description C")
        )
    }

    //adds book to reading list
    fun addBookToReadingList(book: Book, addedBy: String) {
        viewModelScope.launch {
            BookRepository.addBook(book, addedBy)
        }
    }

}
