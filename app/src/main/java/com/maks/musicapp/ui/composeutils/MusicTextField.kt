package com.maks.musicapp.ui.composeutils

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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

@Composable
fun MusicTextField(textValue:String,onValueChange:(String)->Unit){
    TextField(value = textValue, onValueChange = onValueChange,
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
        }, label = {
            Text("Enter song name or artist...")
        })
}