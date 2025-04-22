package com.kds.bookclub.viewmodels
//reading list state, for users to read
import androidx.lifecycle.ViewModel
import com.kds.bookclub.data.models.ReadingListEntry
import com.kds.bookclub.data.repositories.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReadingListViewModel : ViewModel() {
    private val _books = MutableStateFlow<List<ReadingListEntry>>(emptyList())
    val books: StateFlow<List<ReadingListEntry>> = _books

    init {
        BookRepository.observeReadingList {
            _books.value = it
        }
    }
}
