package com.kds.bookclub.data.models
//represents object book retrieved from Google Books API or mock data
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val thumbnail: String?,
    val description: String?
) : Parcelable
