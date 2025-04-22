package com.kds.bookclub.api
// api handler, search request handler
// includes fallback for mock data... had many issues with setting up apis
//this is the fourth api that I attempt to use.
import com.kds.bookclub.data.models.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import com.kds.bookclub.BuildConfig

object GoogleBooksApi {
    private const val BASE_URL = "https://www.googleapis.com/books/v1/volumes?q="
    private const val API_KEY = BuildConfig.GOOGLE_BOOKS_API_KEY

    suspend fun searchBooks(query: String): List<Book> = withContext(Dispatchers.IO) {
        val encoded = URLEncoder.encode(query, "UTF-8")
        val url = URL("$BASE_URL$encoded")
        val connection = url.openConnection() as HttpURLConnection

        return@withContext try {
            connection.requestMethod = "GET"
            connection.connect()

            if (connection.responseCode == 200) {
                val stream = BufferedReader(InputStreamReader(connection.inputStream))
                val response = stream.use { it.readText() }
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
            } else {
                println("[ERROR] Google Books API responded with ${connection.responseCode}: ${connection.responseMessage}")
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        } finally {
            connection.disconnect()
        }
    }
}
