package com.kds.bookclub.data.models

data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val thumbnail: String?,
    val description: String?
)
