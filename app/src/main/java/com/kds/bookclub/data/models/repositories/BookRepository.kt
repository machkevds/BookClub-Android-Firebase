package com.kds.bookclub.data.repositories

// helps with reading list interactions with Firestore
// manages adding/removing books and observing shared list state

import com.google.firebase.firestore.FirebaseFirestore
import com.kds.bookclub.data.models.Book
import com.kds.bookclub.data.models.ReadingListEntry
import kotlinx.coroutines.tasks.await

object BookRepository {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("reading_list")

    suspend fun addBook(book: Book, addedBy: String) {
        val entry = ReadingListEntry(
            id = book.id,
            title = book.title,
            authors = book.authors,
            thumbnail = book.thumbnail,
            addedBy = addedBy
        )
        collection.document(book.id).set(entry).await()
    }

    fun observeReadingList(onUpdate: (List<ReadingListEntry>) -> Unit) {
        collection.addSnapshotListener { snapshot, _ ->
            val list = snapshot?.documents?.mapNotNull {
                it.toObject(ReadingListEntry::class.java)
            } ?: emptyList()
            onUpdate(list)
        }
    }
}
