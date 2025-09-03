package com.coopersimpson.androidnews.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
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

@Composable
fun RandomOptionsScreen(
    modifier: Modifier = Modifier, contentPadding: PaddingValues = PaddingValues()
) {
    val countries = listOf("Australia", "New Zealand")
    val languages = listOf("English", "Mandarin")
    val categories = listOf("General", "Technology")

    var selectedCountry by rememberSaveable { mutableStateOf(countries.first()) }
    var selectedLanguage by rememberSaveable { mutableStateOf(languages.first()) }
    var selectedCategory by rememberSaveable { mutableStateOf(categories.first()) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = contentPadding,
    ) {
        item {
            LabeledDropdown(
                label = "Country",
                options = countries,
                selected = selectedCountry,
                onSelected = { selectedCountry = it }
            )
        }
        item {
            LabeledDropdown(
                label = "Language",
                options = languages,
                selected = selectedLanguage,
                onSelected = { selectedLanguage = it }
            )
        }
        item {
            LabeledDropdown(
                label = "Category",
                options = categories,
                selected = selectedCategory,
                onSelected = { selectedCategory = it }
            )
        }
        item {
            Button(onClick = { }) {
                Text("I'm feeling lucky!")
            }
        }
    }
}