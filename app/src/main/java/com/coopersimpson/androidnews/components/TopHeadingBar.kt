package com.coopersimpson.androidnews.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopHeadingBar(
    articleTitle: String = "Android News",
    onDismiss: () -> Unit = { },
    showCloseActionButton: Boolean = false
) {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = articleTitle,
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFFFFFFFF),
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            if (showCloseActionButton) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = Color(0xFFFFFFFF),
                        contentDescription = "Close Web view"
                    )
                }
            }
        }
    )
}