package com.coopersimpson.androidnews.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.coopersimpson.androidnews.BuildConfig
import com.coopersimpson.androidnews.presentation.ListNewsScreenViewModel

@Composable
fun DebugMenu(vm: ListNewsScreenViewModel) {
    Text(
        text = BuildConfig.VERSION_NAME, modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        textAlign = TextAlign.Center
    )
    OutlinedButton(
        onClick = { vm.clearRoomCache() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text("Clear Room DB cache")
    }
}