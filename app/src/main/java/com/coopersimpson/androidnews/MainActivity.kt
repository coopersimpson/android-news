package com.coopersimpson.androidnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.coopersimpson.androidnews.components.RefreshButton
import com.coopersimpson.androidnews.components.TopHeadingBar
import com.coopersimpson.androidnews.presentation.ListNewsScreen
import com.coopersimpson.androidnews.presentation.ListNewsScreenViewModel
import com.coopersimpson.androidnews.presentation.RandomOptionsScreen
import com.coopersimpson.androidnews.ui.theme.AndroidNewsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidNewsTheme {
                val snackbarHostState = remember { SnackbarHostState() }

                val vm: ListNewsScreenViewModel = hiltViewModel()
                val articles by vm.articles.collectAsStateWithLifecycle()
                val error by vm.error.collectAsStateWithLifecycle()

                val params = vm.queryParams.collectAsStateWithLifecycle().value

                val showFab = error != null && articles.isEmpty()

                val nav = rememberNavController()
                val navBackStackEntry by nav.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopHeadingBar() },
                    floatingActionButton = {
                        AnimatedVisibility(visible = !showFab) {
                            RefreshButton(
                                onClick = {
                                    if (currentRoute == "news") {
                                        nav.navigate("random")
                                    } else {
                                        vm.loadNews(
                                            query = params.q,
                                            language = params.language,
                                            category = params.category,
                                            country = params.country
                                        )
                                        nav.popBackStack() // Prefer .popBackStack over .navigate to preserve state of previous screen
                                    }
                                },
                                isOnRandom = currentRoute == "random"
                            )
                        }
                    },
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) { innerPadding ->
                    NavHost(
                        navController = nav,
                        startDestination = "news"
                    ) {
                        composable("news") {
                            ListNewsScreen(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = innerPadding,
                                snackbarHostState = snackbarHostState,
                                latestParams = params,
                                vm = vm
                            )
                        }
                        composable("random") {
                            RandomOptionsScreen(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = innerPadding,
                                vm = vm
                            )
                        }
                    }
                }
            }
        }
    }
}