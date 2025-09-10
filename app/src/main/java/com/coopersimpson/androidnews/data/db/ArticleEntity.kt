package com.coopersimpson.androidnews.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val link: String,
    val title: String? = null,
    val pubDate: String? = null,
    val categories: List<String> = emptyList(),
    val imageUrl: String?,
    val fetchedAt: Long
)