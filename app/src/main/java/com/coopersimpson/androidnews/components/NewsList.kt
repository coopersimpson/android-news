import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coopersimpson.androidnews.components.DebugMenu
import com.coopersimpson.androidnews.components.NewsCard
import com.coopersimpson.androidnews.components.QueryParamsRow
import com.coopersimpson.androidnews.data.network.Article
import com.coopersimpson.androidnews.data.network.QueryParams
import com.coopersimpson.androidnews.presentation.CustomWebView
import com.coopersimpson.androidnews.presentation.ListNewsScreenViewModel

@Composable
fun NewsList(
    articles: List<Article>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    latestParams: QueryParams,
    vm: ListNewsScreenViewModel
) {
    var openUrl by rememberSaveable { mutableStateOf<String?>(null) }
    var openTitle by rememberSaveable { mutableStateOf<String?>(null) }

    val showHeader by vm.showQueryParams.collectAsStateWithLifecycle()

    // List of NewsCard
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = contentPadding
    ) {
        if (showHeader) {
            item { QueryParamsRow(latestParams) }
        }

        items(articles) { a ->
            NewsCard(
                title = a.title ?: "Untitled",
                date = a.pubDate ?: "-",
                categories = a.category,
                countries = a.country,
                imageUrl = a.image_url ?: "",
                onClick = {
                    openUrl = a.link
                    openTitle = a.title
                }
            )
        }
        item {
            // Include a debug menu for develop productFlavor
            DebugMenu(vm)
        }
    }
    if (openUrl != null) {
        CustomWebView(
            url = openUrl!!,
            onDismiss = { openUrl = null; openTitle = null },
            articleTitle = openTitle ?: "Untitled"
        )
    }
}
