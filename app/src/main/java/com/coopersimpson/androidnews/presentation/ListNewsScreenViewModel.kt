package com.coopersimpson.androidnews.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coopersimpson.androidnews.data.ArticleRepository
import com.coopersimpson.androidnews.data.network.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListNewsScreenViewModel @Inject constructor(private val repo: ArticleRepository) :
    ViewModel() {
    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadNews()
    }

    fun loadNews(
        query: String? = null,
        language: String? = "en",
        category: String? = null,
        country: String? = null
    ) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val result = repo.fetchLatestCached(
                    q = query,
                    language = language,
                    category = category,
                    country = country
                )

                if (result.fromCache && result.error != null) {
                    _error.value = "You're offline. Reconnect to load more articles"
                } else if (result.articles.isEmpty()) {
                    _error.value = "No articles found for this filter."
                }

                if (result.articles.isNotEmpty()) {
                    _articles.value = result.articles
                }

            } catch (t: Throwable) {
                _error.value = t.message ?: "Unknown error"
            } finally {
                _loading.value = false
            }
        }
    }
}