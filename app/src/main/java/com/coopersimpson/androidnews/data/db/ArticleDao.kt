package com.coopersimpson.androidnews.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles ORDER BY pubDate DESC")
    suspend fun getAll(): List<ArticleEntity>

    @Query("DELETE FROM articles")
    suspend fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<ArticleEntity>)
}