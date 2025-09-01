package com.coopersimpson.androidnews.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coopersimpson.androidnews.components.NewsCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListNewsScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    snackbarHostState: SnackbarHostState,
    vm: ListNewsScreenViewModel = hiltViewModel()
) {
    val articles by vm.articles.collectAsState()
    val loading by vm.loading.collectAsState()
    val error by vm.error.collectAsState()

    // This triggers a snackbar whenever the value of error changes
    LaunchedEffect(error) {
        error?.let { snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short) }
    }

    val ptrState = rememberPullToRefreshState()

    Box(modifier = modifier.fillMaxSize()) {
        when {
            loading && articles.isEmpty() -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }

            error != null && articles.isEmpty() -> {
                PullToRefreshBox(
                    isRefreshing = false,
                    onRefresh = { vm.loadNews() },
                    state = ptrState,
                    indicator = {
                        Indicator(
                            modifier = Modifier.align(Alignment.TopCenter),
                            isRefreshing = false,
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            state = ptrState
                        )
                    },
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = contentPadding
                    ) {
                        item {
                            Box(
                                modifier = Modifier.fillParentMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Could not load articles.\nPull to refresh or try again later.")
                            }
                        }
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = modifier,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = contentPadding
                ) {
                    items(articles) { a ->
                        NewsCard(a.title ?: "Untitled")
                    }
                }

                if (loading) {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

        }
    }
}