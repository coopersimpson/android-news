// Imports you'll likely need:
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.coopersimpson.androidnews.components.NewsCard
import com.coopersimpson.androidnews.data.network.Article

@Composable
fun NewsList(
    articles: List<Article>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    var openUrl by rememberSaveable { mutableStateOf<String?>(null) }

    // List of NewsCard
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = contentPadding
    ) {
        items(articles) { a ->
            NewsCard(
                a.title ?: "Untitled",
                onClick = { a.link?.let { openUrl = it } } // open WebView
            )
        }
    }

    // WebView dialog
    openUrl?.let { url ->
        Dialog(
            onDismissRequest = { openUrl = null },
            properties = DialogProperties(usePlatformDefaultWidth = false) // Full screen WebView
        ) {
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
