package com.example.day4task1booksfilterapplication

import retrofit2.Call
import retrofit2.http.GET

interface HttpApiService {
    @GET("/books")
    suspend fun getBooks(): List<BooksResult>
}