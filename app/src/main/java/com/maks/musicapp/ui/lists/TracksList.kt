package com.maks.musicapp.ui.lists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.maks.musicapp.R
import com.maks.musicapp.data.music.TrackResult
import com.maks.musicapp.ui.animation.DisplayShimmer
import com.maks.musicapp.viewmodels.TrackViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun TracksList(trackViewModel: TrackViewModel) {
    val tracks by trackViewModel.trackListLiveData.observeAsState()
    val isLoading by trackViewModel.isLoading
    DisplayShimmer(isLoading)
    tracks?.value?.let { trackList->
        LazyColumn {
            items(trackList) {
                TracksListItem(trackResult = it)
            }
        }
    }


}

@Composable
fun TracksListItem(trackResult: TrackResult) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column {
            GlideImage(
                imageModel = trackResult.image,
                contentScale = ContentScale.Crop,
                placeHolder = ImageBitmap.imageResource(R.drawable.music_background),
            )
            Text(text = trackResult.name, fontWeight = FontWeight.Bold)
            Text(text = trackResult.artist_name)
        }
    }

}