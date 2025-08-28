package com.coopersimpson.androidnews.data

import com.coopersimpson.androidnews.data.network.Article
import com.coopersimpson.androidnews.data.network.NewsApi
import javax.inject.Inject

class ArticleRepository @Inject constructor(private val api: NewsApi) {
    suspend fun fetchArticleNames(q: String? = null): List<Article> {
        val res = api.latest(q = q, language = "en")
        return res.results.take(10)
    }
}