package com.coopersimpson.androidnews.data.network

import retrofit2.http.GET
import retrofit2.http.Query

data class NewsResponse(
    val results: List<Article> = emptyList()
)

data class Article(
    val title: String? = null,
    val link: String? = null
)

interface NewsApi {
    @GET("latest") // latest is the endpoint https://newsdata.io/api/1/ + latest
    suspend fun latest(
        @Query("q") q: String? = null,
        @Query("language") language: String? = "en"
    ): NewsResponse
}