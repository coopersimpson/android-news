package com.coopersimpson.androidnews.data.db

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString("||")
    }

    @TypeConverter
    fun toList(serialized: String): List<String> {
        if (serialized.isBlank()) {
            return emptyList()
        } else {
            return serialized.split("||")
        }
    }
}