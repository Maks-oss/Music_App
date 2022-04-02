package com.maks.musicapp.ui.lists

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.maks.musicapp.R
import com.maks.musicapp.data.music.track.TrackResult
import com.maks.musicapp.ui.states.TracksUiState
import com.maks.musicapp.ui.animation.DisplayShimmer
import com.maks.musicapp.utils.AppConstants
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

@ExperimentalFoundationApi
@Composable
fun TracksList(
//    isLoading:Boolean,
    tracksUiState: TracksUiState,
    listScrollAction: (LazyListState) -> Unit,
    trackListItemClickAction: (TrackResult) -> Unit
) {
//    val tracks by tracksLiveData.observeAsState()
    val scrollState = rememberLazyListState()
    listScrollAction(scrollState)
    DisplayShimmer(tracksUiState.isLoading)
    DisplayTrackList(tracksUiState.tracksResult, scrollState, trackListItemClickAction)

}

@ExperimentalFoundationApi
@Composable
fun DisplayTrackList(
    tracks: List<TrackResult>?,
    scrollState: LazyListState,
    trackListItemClickAction: (TrackResult) -> Unit
) {
    tracks?.let { trackList ->
        LazyVerticalGrid(cells = GridCells.Fixed(2), state = scrollState) {
            items(trackList) {
                TracksListItem(
                    trackResult = it,
                    trackListItemClickAction = trackListItemClickAction
                )
            }
        }
    }
}

@Composable
fun TracksListItem(trackResult: TrackResult, trackListItemClickAction: (TrackResult) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = { trackListItemClickAction(trackResult) }), elevation = 8.dp
    ) {
        Column {
            GlideImage(
                imageModel = trackResult.image?:AppConstants.DEFAULT_IMAGE,
                contentScale = ContentScale.Crop,
                circularReveal = CircularReveal(),
                placeHolder = ImageBitmap.imageResource(R.drawable.music_logo),

            )
            Text(
                text = "${trackResult.artist_name} - ${trackResult.name}",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
        }
    }

}

