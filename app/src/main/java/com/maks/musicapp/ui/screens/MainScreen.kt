package com.maks.musicapp.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.maks.musicapp.ui.lists.TracksList
import com.maks.musicapp.viewmodels.TrackViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

@Composable
fun MainScreen(trackViewModel: TrackViewModel) {
    val textValue = rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    var currentJob by remember { mutableStateOf<Job?>(null) }
    Column {
        TextField(value = textValue.value, onValueChange = {
            textValue.value = it
            currentJob?.cancel()
            if (it.isNotEmpty()) {
                currentJob = scope.async {
                    textValue.value = it
                    delay(1000)
                    trackViewModel.findTracksByName(it)
                }
            }

        },
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
        TracksList(trackViewModel)
    }

}

