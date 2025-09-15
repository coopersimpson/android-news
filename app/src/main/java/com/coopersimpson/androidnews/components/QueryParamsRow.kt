package com.coopersimpson.androidnews.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.coopersimpson.androidnews.data.network.QueryParams

@Composable
fun QueryParamsRow(latestParams: QueryParams) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // TODO: need to give clearer mappings for the user, use a reverse from ApiMappings
        Text(
            text = "Category: ${latestParams.category?.takeIf { it.isNotBlank() } ?: "-"}",
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "Language: ${latestParams.language?.takeIf { it.isNotBlank() } ?: "-"}",
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "Country: ${latestParams.country?.takeIf { it.isNotBlank() } ?: "-"}",
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
    }
}
