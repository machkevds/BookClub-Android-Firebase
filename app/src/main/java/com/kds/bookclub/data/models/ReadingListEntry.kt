package com.kds.bookclub.data.models

import android.util.Log

//entry object, represented in the shared reading list
data class ReadingListEntry(

    val id: String = "",
    val title: String = "",
    val authors: List<String> = listOf(),
    val thumbnail: String? = null,
    val addedBy: String = ""
)

fun ReadingListEntry.toBook(): Book {
    Log.d("ReadingListEntry", "Converting to Book: id=$id, title=$title, authors=$authors (${authors::class.simpleName})")
    return Book(
        id = id,
        title = title,
        authors = authors,
        thumbnail = thumbnail,
        description = null
    )
}