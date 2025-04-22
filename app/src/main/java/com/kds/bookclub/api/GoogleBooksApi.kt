package com.kds.bookclub.api

import com.kds.bookclub.data.models.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.net.URLEncoder

object GoogleBooksApi {
    private const val BASE_URL = "https://www.googleapis.com/books/v1/volumes?q="

    suspend fun searchBooks(query: String): List<Book> = withContext(Dispatchers.IO) {
        val encodedQuery = URLEncoder.encode(query, "UTF-8")
        val response = URL("$BASE_URL$encodedQuery").readText()
        val json = JSONObject(response)
        val items = json.optJSONArray("items") ?: return@withContext emptyList()

        List(items.length()) { i ->
            val item = items.getJSONObject(i)
            val volumeInfo = item.getJSONObject("volumeInfo")
            Book(
                id = item.getString("id"),
                title = volumeInfo.optString("title", "Untitled"),
                authors = volumeInfo.optJSONArray("authors")?.let { a ->
                    List(a.length()) { a.getString(it) }
                } ?: listOf("Unknown"),
                thumbnail = volumeInfo.optJSONObject("imageLinks")?.optString("thumbnail"),
                description = volumeInfo.optString("description", null)
            )
        }
    }
}
