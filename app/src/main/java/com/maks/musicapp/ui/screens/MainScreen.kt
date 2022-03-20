package com.maks.musicapp.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen() {
    val textValue = rememberSaveable { mutableStateOf("") }
    TextField(value = textValue.value, onValueChange = { textValue.value = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(CircleShape)
            .border(
                1.dp,
                Color.Black, CircleShape
            ),
        leadingIcon = {
            Icon(Icons.Filled.Search, "")
        },label = {
            Text("Enter song name or artist...")
        })
}

