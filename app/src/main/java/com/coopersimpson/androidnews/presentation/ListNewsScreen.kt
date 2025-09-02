package com.coopersimpson.androidnews.presentation

import NewsList
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListNewsScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    snackbarHostState: SnackbarHostState,
    vm: ListNewsScreenViewModel
) {
    val articles by vm.articles.collectAsStateWithLifecycle()
    val loading by vm.loading.collectAsStateWithLifecycle()
    val error by vm.error.collectAsStateWithLifecycle()

    // This triggers a snackbar whenever the value of error changes
    LaunchedEffect(error) {
        error?.let { snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short) }
    }

    val ptrState = rememberPullToRefreshState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp) // give space between news items and TopAppBar
    ) {
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
                NewsList(
                    articles,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = contentPadding
                )
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