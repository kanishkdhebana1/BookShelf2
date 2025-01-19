package com.example.bookshelf2.data

import com.example.bookshelf2.model.BookDetailsResponse
import com.example.bookshelf2.model.BookSearchResponse
import com.example.bookshelf2.network.BookShelfApiService
import retrofit2.Call

interface BookshelfRepository {
    suspend fun searchBooks(query: String, page: Int, limit: Int): BookSearchResponse
    suspend fun searchBookDetails(key: String): BookDetailsResponse
}

class NetworkBookShelfRepository(
    private val bookShelfApiService: BookShelfApiService
): BookshelfRepository {
    override suspend fun searchBooks(query: String, page: Int, limit: Int): BookSearchResponse =
        bookShelfApiService.searchBooks(query, page = page, limit = limit)

    override suspend fun searchBookDetails(key: String): BookDetailsResponse =
        bookShelfApiService.searchBookDetails(key)
}
