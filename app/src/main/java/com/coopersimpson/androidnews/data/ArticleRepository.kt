package com.coopersimpson.androidnews.data

import com.coopersimpson.androidnews.data.db.ArticleDao
import com.coopersimpson.androidnews.data.network.Article
import com.coopersimpson.androidnews.data.network.NewsApi
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val api: NewsApi,
    private val dao: ArticleDao
) {
    suspend fun fetchLatestCached(
        q: String? = null,
        language: String? = "en", // Default to english articles
        category: String? = null,
        country: String? = null
    ): List<Article> {
        return try {
            // Network first
            val res = api.latest(q = q, language = language, category = category, country = country)
            val latest = res.results.take(10)

            // Replace old cache if we can load from API (clear and then insert)
            val now = System.currentTimeMillis()
            dao.clear()
            dao.upsertAll(latest.map { it.toEntity(now) })

            latest
        } catch (_: Throwable) {
            // Offline/error, therefore load the cached articles from DB and convert/return them
            dao.getAll().map { it.toDomain() }
        }
    }
}