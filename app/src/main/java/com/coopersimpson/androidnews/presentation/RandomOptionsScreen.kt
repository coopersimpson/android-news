package com.coopersimpson.androidnews.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coopersimpson.androidnews.components.LabeledDropdown
import com.coopersimpson.androidnews.data.network.ApiMappings
import com.coopersimpson.androidnews.data.network.QueryParams

@Composable
fun RandomOptionsScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    onParamsChange: (QueryParams) -> Unit
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedCountry by rememberSaveable { mutableStateOf(ApiMappings.countryLabels.first()) }
    var selectedLanguage by rememberSaveable { mutableStateOf(ApiMappings.languageLabels.first()) }
    var selectedCategory by rememberSaveable { mutableStateOf(ApiMappings.categoryLabels.first()) }

    fun pushParams() = onParamsChange(
        QueryParams(
            q = searchQuery.ifBlank { null },
            language = ApiMappings.languageCode(selectedLanguage),
            category = ApiMappings.categoryCode(selectedCategory),
            country = ApiMappings.countryCode(selectedCountry)
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = contentPadding,
    ) {
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it; pushParams() },
                label = { Text("Search articles") },
            )
        }
        item {
            LabeledDropdown(
                label = "Country",
                options = ApiMappings.countryLabels,
                selected = selectedCountry,
                onSelected = { selectedCountry = it; pushParams() }
            )
        }
        item {
            LabeledDropdown(
                label = "Language",
                options = ApiMappings.languageLabels,
                selected = selectedLanguage,
                onSelected = { selectedLanguage = it; pushParams() }
            )
        }
        item {
            LabeledDropdown(
                label = "Category",
                options = ApiMappings.categoryLabels,
                selected = selectedCategory,
                onSelected = { selectedCategory = it; pushParams() }
            )
        }
    }
}