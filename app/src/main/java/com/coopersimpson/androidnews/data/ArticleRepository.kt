package com.coopersimpson.androidnews.data

import com.coopersimpson.androidnews.data.network.Article
import com.coopersimpson.androidnews.data.network.NewsApi
import javax.inject.Inject

class ArticleRepository @Inject constructor(private val api: NewsApi) {
    suspend fun fetchArticles(
        q: String? = null,
        language: String? = "en", // Default to english articles
        category: String? = null,
        country: String? = null
    ): List<Article> {
        val res = api.latest(
            q = q,
            language = language,
            category = category,
            country = country
        )
        return res.results.take(10)
    }
}