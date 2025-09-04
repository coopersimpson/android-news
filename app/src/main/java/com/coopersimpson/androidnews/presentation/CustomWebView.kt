package com.coopersimpson.androidnews.presentation

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.coopersimpson.androidnews.components.TopHeadingBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomWebView(
    url: String,
    onDismiss: () -> Unit,
    articleTitle: String?
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false) // Full screen WebView
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopHeadingBar(articleTitle ?: "Untitled")
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    WebView(ctx).apply {
                        settings.javaScriptEnabled = true
                        webViewClient = WebViewClient()
                        loadUrl(url)
                    }
                }
            )
        }
    }
}