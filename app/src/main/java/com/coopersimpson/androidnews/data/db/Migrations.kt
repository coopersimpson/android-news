package com.coopersimpson.androidnews.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Add the new column to support imageUrl for coil caching
        db.execSQL("ALTER TABLE articles ADD COLUMN imageUrl TEXT")
    }
}