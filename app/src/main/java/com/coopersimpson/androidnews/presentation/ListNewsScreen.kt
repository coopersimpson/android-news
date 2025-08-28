package com.coopersimpson.androidnews.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ListNewsScreen(modifier: Modifier = Modifier, vm: ListNewsScreenViewModel = hiltViewModel()) {
    val articles by vm.articles.collectAsState()
    val loading by vm.loading.collectAsState()
    val error by vm.error.collectAsState()

    when {
        loading -> Text("Loading...", modifier)
        error != null -> Text("Error: $error", modifier)
        else -> LazyColumn(modifier = modifier) {
            items(articles) { a ->
                Text(a.title ?: "Untitled", Modifier.padding(top = 16.dp))
            }
        }
    }
}