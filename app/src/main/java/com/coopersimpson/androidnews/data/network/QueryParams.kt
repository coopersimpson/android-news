package com.coopersimpson.androidnews.data.network

data class QueryParams(
    val q: String? = null,
    val language: String? = "en",
    val category: String? = null,
    val country: String? = null
)