package com.coopersimpson.androidnews.presentation

import android.graphics.Bitmap
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            val ctx = LocalContext.current
            var isLoading by remember { mutableStateOf(true) }

            Column(modifier = Modifier.fillMaxSize()) {
                TopHeadingBar(
                    articleTitle = articleTitle ?: "Untitled",
                    onDismiss = onDismiss,
                    showCloseActionButton = true
                )

                Box(Modifier.fillMaxSize()) {
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = {
                            WebView(ctx).apply {
                                settings.javaScriptEnabled = true
                                settings.domStorageEnabled = true
                                settings.databaseEnabled = true
                                settings.loadsImagesAutomatically = true

                                CookieManager.getInstance().setAcceptCookie(true)

                                webViewClient = object : WebViewClient() {
                                    override fun onPageStarted(
                                        view: WebView?,
                                        url: String?,
                                        favicon: Bitmap?
                                    ) {
                                        isLoading = true
                                    }

                                    override fun onPageFinished(view: WebView?, url: String?) {
                                        isLoading = false
                                    }

                                    override fun onReceivedError(
                                        view: WebView?,
                                        errorCode: Int,
                                        description: String?,
                                        failingUrl: String?
                                    ) {
                                        // Hide spinner even with error (prevent stuck UI)
                                        isLoading = false
                                    }
                                }
                                webChromeClient = object : WebChromeClient() {
                                    override fun onProgressChanged(
                                        view: WebView?,
                                        newProgress: Int
                                    ) {
                                        if (newProgress >= 100) isLoading = false
                                    }
                                }
                                loadUrl(url)
                            }
                        },
                        update = { webView ->
                            // If the URL changes, load the new one
                            if (webView.url != url) {
                                isLoading = true
                                webView.loadUrl(url)
                            }
                        }
                    )

                    if (isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}