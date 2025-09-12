package com.coopersimpson.androidnews.data

import com.coopersimpson.androidnews.data.db.ArticleDao
import com.coopersimpson.androidnews.data.network.Article
import com.coopersimpson.androidnews.data.network.NewsApi
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val api: NewsApi,
    private val dao: ArticleDao
) {
    // Type to capture fetched result
    data class FetchResult(
        val articles: List<Article>,
        val fromCache: Boolean,
        val error: Throwable? = null
    )

    suspend fun fetchLatestCached(
        q: String? = null,
        language: String? = "en", // Default to english articles
        category: String? = null,
        country: String? = null
    ): FetchResult {
        return try {
            val res = api.latest(q, language, category, country)
            val latest = res.results.take(10)

            // Only replace cache if the latest API call is not empty
            if (latest.isNotEmpty()) {
                val now = System.currentTimeMillis()
                dao.clear()
                dao.upsertAll(latest.map { it.toEntity(now) })
            }

            FetchResult(articles = latest, fromCache = false, error = null)

        } catch (t: Throwable) {
            val cached = dao.getAll().map { it.toDomain() }

            FetchResult(articles = cached, fromCache = true, error = t)
        }
    }

    suspend fun clearRoomCache() {
        dao.clear()
    }
}