package com.example.bookshelf2.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Transient

@Serializable
data class BookSearchResponse(
    val start: Int,
    val numFound: Int,
    val docs: List<BookItem>
)



@Serializable
data class BookItem(
    @SerialName("cover_i") val coverId: Int? = null,
    @Transient var id: Int = 0,
    val title: String,
    @SerialName("author_name") val authors: List<String>? = null,
    @SerialName("publish_year") val firstPublishYear: List<Int>? = null,
)

@Serializable
data class VolumeInfo(
    val title: String,
    val imageLinks: ImageLinks? = null,
    val description: String? = null,
    val authors: List<String>? = null
)

@Serializable
data class ImageLinks (
    @SerialName("thumbnail") val thumbnail: String? = null,
    @SerialName("extraLarge") val extraLarge: String? = null,
    @SerialName("large") val large: String? = null,
    @SerialName("medium") val medium: String? = null
)

@Serializable
data class Book(
    val id: String,
    val title: String,
    val thumbnailUrl: String? = null,
    val selfLink: String,
    val description: String?,
    val authors: List<String>?
)
