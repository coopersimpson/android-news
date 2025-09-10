package com.coopersimpson.androidnews.data

import com.coopersimpson.androidnews.data.db.ArticleEntity
import com.coopersimpson.androidnews.data.network.Article
import java.util.UUID

fun Article.toEntity(now: Long): ArticleEntity = ArticleEntity(
    title = title,
    link = link ?: UUID.randomUUID().toString(), // fallback if API omits link
    pubDate = pubDate,
    categories = category,
    imageUrl = image_url,
    fetchedAt = now
)

fun ArticleEntity.toDomain(): Article = Article(
    title = title,
    link = link,
    pubDate = pubDate,
    category = categories,
    image_url = imageUrl
)