package com.kds.bookclub.viewmodels
//reading list state, for users to read
import android.util.Log
import androidx.lifecycle.ViewModel
import com.kds.bookclub.data.models.ReadingListEntry
import com.kds.bookclub.data.repositories.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReadingListViewModel : ViewModel() {
    private val _readingList = MutableStateFlow<List<ReadingListEntry>>(emptyList())
    val books: StateFlow<List<ReadingListEntry>> = _readingList

    init {
        Log.d("ReadingListViewModel", "Initializing ViewModel")
        BookRepository.observeReadingList { list ->
            Log.d("ReadingListViewModel", "Received ${list.size} entries from Firestore")
            list.forEach { entry ->
                Log.d("ReadingListViewModel", "Entry: id=${entry.id}, title=${entry.title}, authors=${entry.authors}")
            }
            _readingList.value = list
        }
    }
}
