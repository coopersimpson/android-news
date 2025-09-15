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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coopersimpson.androidnews.components.LabeledDropdown
import com.coopersimpson.androidnews.data.network.ApiMappings

@Composable
fun RandomOptionsScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    vm: ListNewsScreenViewModel
) {
    val queryParams by vm.queryParams.collectAsStateWithLifecycle()
    val country by vm.selectedCountryLabel.collectAsStateWithLifecycle()
    val language by vm.selectedLanguageLabel.collectAsStateWithLifecycle()
    val category by vm.selectedCategoryLabel.collectAsStateWithLifecycle()

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
                value = queryParams.q ?: "",
                onValueChange = { vm.onSearchQueryChanged(it) },
                label = { Text("Search articles") },
            )
        }
        item {
            LabeledDropdown(
                label = "Country",
                options = ApiMappings.countryLabels,
                selected = country,
                onSelected = { vm.onCountryChanged(it) }
            )
        }
        item {
            LabeledDropdown(
                label = "Language",
                options = ApiMappings.languageLabels,
                selected = language,
                onSelected = { vm.onLanguageChanged(it) }
            )
        }
        item {
            LabeledDropdown(
                label = "Category",
                options = ApiMappings.categoryLabels,
                selected = category,
                onSelected = { vm.onCategoryChanged(it) }
            )
        }
    }
}