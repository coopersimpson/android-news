package com.coopersimpson.androidnews.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coopersimpson.androidnews.data.ArticleRepository
import com.coopersimpson.androidnews.data.network.ApiMappings
import com.coopersimpson.androidnews.data.network.Article
import com.coopersimpson.androidnews.data.network.QueryParams
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

    private val _showQueryParams = MutableStateFlow(false)
    val showQueryParams: StateFlow<Boolean> = _showQueryParams

    private val _queryParams = MutableStateFlow(QueryParams(language = "en"))
    val queryParams: StateFlow<QueryParams> = _queryParams

    val selectedCountryLabel =
        MutableStateFlow(ApiMappings.countryLabel(_queryParams.value.country))
    val selectedLanguageLabel =
        MutableStateFlow(ApiMappings.languageLabel(_queryParams.value.language))
    val selectedCategoryLabel =
        MutableStateFlow(ApiMappings.categoryLabel(_queryParams.value.category))

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
                    _showQueryParams.value = false
                } else if (result.articles.isEmpty()) {
                    _error.value = "No articles found for this filter."
                    _showQueryParams.value = false
                }

                if (result.articles.isNotEmpty()) {
                    _articles.value = result.articles
                    _showQueryParams.value = true
                    // Hide the query params if we are offline
                    if (result.fromCache && result.error != null) {
                        _showQueryParams.value = false
                    }
                }

            } catch (t: Throwable) {
                _error.value = t.message ?: "Unknown error"
            } finally {
                _loading.value = false
            }
        }
    }

    fun clearRoomCache() {
        viewModelScope.launch {
            // Clear the Room db cache
            repo.clearRoomCache()
            // Clear out the article VM
            _articles.value = emptyList()
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        _queryParams.value = _queryParams.value.copy(
            q = newQuery.ifBlank { null }
        )
    }

    fun onCountryChanged(newLabel: String) {
        val code = ApiMappings.countryCode(newLabel)
        _queryParams.value = _queryParams.value.copy(country = code)
        selectedCountryLabel.value = ApiMappings.countryLabel(code)
    }

    fun onLanguageChanged(newLabel: String) {
        val code = ApiMappings.languageCode(newLabel)
        _queryParams.value = _queryParams.value.copy(language = code)
        selectedLanguageLabel.value = ApiMappings.languageLabel(code)
    }

    fun onCategoryChanged(newLabel: String) {
        val code = ApiMappings.categoryCode(newLabel)
        _queryParams.value = _queryParams.value.copy(category = code)
        selectedCategoryLabel.value = ApiMappings.categoryLabel(code)
    }

    fun pickRandomQuery() {
        // Pick random labels for UI
        val countryLabel = ApiMappings.countryLabels.random()
        val languageLabel = ApiMappings.languageLabels.random()
        val categoryLabel = ApiMappings.categoryLabels.random()
        // Pick random query codes
        val countryCode = ApiMappings.countryCode(countryLabel)
        val languageCode = ApiMappings.languageCode(languageLabel)
        val categoryCode = ApiMappings.categoryCode(categoryLabel)

        // Set query params
        _queryParams.value = _queryParams.value.copy(
            country = countryCode,
            language = languageCode,
            category = categoryCode
        )
        // Set country codes
        selectedCountryLabel.value = countryLabel
        selectedLanguageLabel.value = languageLabel
        selectedCategoryLabel.value = categoryLabel
    }
}