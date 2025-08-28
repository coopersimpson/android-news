package com.coopersimpson.androidnews.data.network

import com.coopersimpson.androidnews.BuildConfig
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

// https://newsdata.io/api/1/latest?apikey=pub_038134549e254b03b27f27276c92c71d&q=YOUR_QUERY&size=10
// Need to define a size 1-10 per query for the free plan

data class NewsResponse(
    val results: List<Article> = emptyList()
)

data class Article(
    val title: String? = null
)

interface NewsApi {
    @GET("latest") // latest is the endpoint https://newsdata.io/api/1/ + latest
    suspend fun latest(
        @Query("q") q: String? = null,
        @Query("language") language: String? = "en"
    ): NewsResponse
}