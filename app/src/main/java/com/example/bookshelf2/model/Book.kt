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

