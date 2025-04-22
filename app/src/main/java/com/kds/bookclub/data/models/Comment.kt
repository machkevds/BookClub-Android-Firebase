package com.kds.bookclub.data.models
// comment object which is left by users on a specific book
data class Comment(
    val id: String = "",
    val bookId: String = "",
    val user: String = "",
    val message: String = "",
    val timestamp: Long = 0L
)
