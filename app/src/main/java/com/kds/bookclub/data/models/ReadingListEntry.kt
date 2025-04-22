package com.kds.bookclub.data.models
//entry object, represented in the shared reading list
data class ReadingListEntry(
    val id: String = "",
    val title: String = "",
    val authors: List<String> = listOf(),
    val thumbnail: String? = null,
    val addedBy: String = ""
)

fun ReadingListEntry.toBook(): Book {
    return Book(
        id = id,
        title = title,
        authors = authors,
        thumbnail = thumbnail,
        description = null
    )
}