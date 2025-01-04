package com.example.bookshelf2.network

import com.example.bookshelf2.model.BookSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookShelfApiService {
    @GET("search.json")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20
    ): BookSearchResponse

    @GET("works/{id}.json")
    suspend fun getBookDetails(
        @Path("id") id: Int
    ): BookSearchResponse
}