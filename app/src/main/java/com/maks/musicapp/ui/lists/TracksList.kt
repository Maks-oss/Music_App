package com.maks.musicapp.ui.lists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.font.FontWeight
import com.maks.musicapp.data.music.TrackResult
import com.maks.musicapp.viewmodels.TrackViewModel

@Composable
fun TracksList(trackViewModel: TrackViewModel) {
    val tracks by trackViewModel.trackListLiveData.observeAsState()
    if (!tracks.isNullOrEmpty()) {
        LazyColumn {
            items(tracks!!) {
                TracksListItem(trackResult = it)
            }
        }
    }
}

@Composable
fun TracksListItem(trackResult: TrackResult) {
    Card() {
        Column() {
            Text(text = trackResult.name, fontWeight = FontWeight.Bold)
            Text(text = trackResult.artist_name)
        }
    }

}