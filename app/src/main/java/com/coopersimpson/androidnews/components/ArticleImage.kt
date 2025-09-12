package com.coopersimpson.androidnews.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.coopersimpson.androidnews.R

@Composable
fun ArticleImage(imageUrl: String?, modifier: Modifier = Modifier) {
    AsyncImage(
        model = imageUrl ?: R.drawable.broken_image,
        contentDescription = null,
        placeholder = painterResource(R.drawable.broken_image),
        error = painterResource(R.drawable.broken_image),
        fallback = painterResource(R.drawable.broken_image),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(72.dp)
            .clip(RoundedCornerShape(8.dp))
    )
}