package com.kds.bookclub.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.kds.bookclub.data.models.Comment
import kotlinx.coroutines.tasks.await

object CommentRepository {
    private val db = FirebaseFirestore.getInstance()

    fun observeComments(bookId: String, onUpdate: (List<Comment>) -> Unit) {
        //firestore listener to observe real time updates on comments
        db.collection("books").document(bookId).collection("comments")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, _ ->
                val comments = snapshot?.documents?.mapNotNull {
                    it.toObject(Comment::class.java)
                } ?: emptyList()
                onUpdate(comments) //call update
            }
    }

    suspend fun addComment(bookId: String, user: String, message: String) {
        val commentRef = db.collection("books").document(bookId).collection("comments").document()
        val comment = Comment(
            id = commentRef.id,
            bookId = bookId,
            user = user,
            message = message,
            timestamp = System.currentTimeMillis() //timestamp for ordering
        )
        //write new comment and wait for completion
        commentRef.set(comment).await()
    }
}
