package com.coopersimpson.androidnews.di

import android.content.Context
import androidx.room.Room
import com.coopersimpson.androidnews.data.db.AppDatabase
import com.coopersimpson.androidnews.data.db.ArticleDao
import com.coopersimpson.androidnews.data.db.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext ctx: Context): AppDatabase =
        Room.databaseBuilder(ctx, AppDatabase::class.java, "androidnews.db")
            .addMigrations(MIGRATION_1_2)
            .build()

    @Provides
    fun provideArticleDao(db: AppDatabase): ArticleDao = db.articleDao()
}