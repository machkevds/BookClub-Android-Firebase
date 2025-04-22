package com.kds.bookclub.viewmodels
//reading list state, for users to read
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kds.bookclub.data.models.ReadingListEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ReadingListViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private val collection = db.collection("reading_lists").document(userId ?: "invalid").collection("books")

    private val _books = MutableStateFlow<List<ReadingListEntry>>(emptyList())
    val books: StateFlow<List<ReadingListEntry>> = _books

    init {
        Log.d("ReadingListViewModel", "Initializing ViewModel")
        collection.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.e("ReadingListViewModel", "Listen failed: $e")
                return@addSnapshotListener
            }
            val entries = snapshot?.documents?.mapNotNull { it.toObject(ReadingListEntry::class.java) } ?: emptyList()
            Log.d("ReadingListViewModel", "Received ${entries.size} entries from Firestore")
            _books.update { entries }
        }
    }

    fun removeBook(bookId: String) {
        Log.d("ReadingListViewModel", "Removing book with id: $bookId")
        collection.document(bookId).delete().addOnSuccessListener {
            Log.d("ReadingListViewModel", "Successfully deleted book with id: $bookId")
        }.addOnFailureListener { e ->
            Log.e("ReadingListViewModel", "Error deleting book: ${e.message}", e)
        }
    }
}
