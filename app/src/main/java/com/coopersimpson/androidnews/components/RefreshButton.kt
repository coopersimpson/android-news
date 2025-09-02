package com.coopersimpson.androidnews.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.coopersimpson.androidnews.R

@Composable
fun RefreshButton(
    onClick: () -> Unit,
    isOnRandom: Boolean,
) {
    FloatingActionButton(
        onClick = { onClick() },
        contentColor = Color(0xFFFFFFFF)
    ) {
        if (isOnRandom) {
            Icon(
                imageVector = Icons.Filled.Done,
                contentDescription = "Pick random properties",
                modifier = Modifier.size(36.dp)
            )
        } else {
            Icon(
                painter = painterResource(R.drawable.die),
                contentDescription = "Roll new articles",
                modifier = Modifier.size(36.dp)
            )
        }
    }
}