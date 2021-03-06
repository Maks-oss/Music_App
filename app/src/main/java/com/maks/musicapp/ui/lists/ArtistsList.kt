package com.maks.musicapp.ui.lists

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.maks.musicapp.R
import com.maks.musicapp.data.domain.Artist
import com.maks.musicapp.ui.animation.DisplayShimmer
import com.maks.musicapp.ui.states.UiState
import com.maks.musicapp.utils.AppConstants
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

@ExperimentalFoundationApi
@Composable
fun ArtistsList(
    artistsUiState: UiState<Artist>,
    listScrollAction: (LazyGridState) -> Unit,
    artistListItemClickAction: (Artist) -> Unit
) {
//    val artists by musicViewModel.artistListLiveData.observeAsState()
//    val isLoading by musicViewModel.musicViewModelStates.isLoading
    val scrollState = rememberLazyGridState()
    listScrollAction(scrollState)
    DisplayShimmer(artistsUiState.isLoading)
    DisplayArtistsList(artistsUiState.result, scrollState, artistListItemClickAction)

}

@ExperimentalFoundationApi
@Composable
fun DisplayArtistsList(
    artists: List<Artist>?,
    scrollState: LazyGridState,
    artistListItemClickAction: (Artist) -> Unit
) {
    artists?.let { artistList ->
        LazyVerticalGrid(columns = GridCells.Fixed(2), state = scrollState) {
            items(artistList) {
                ArtistsListItem(artist = it, artistListItemClickAction)
            }
        }
    }
}

@Composable
fun ArtistsListItem(artist: Artist, artistListItemClickAction: (Artist) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { artistListItemClickAction(artist) },
        elevation = 8.dp
    ) {
        Column {
            GlideImage(
                imageModel = artist.image?.ifEmpty {
                    AppConstants.DEFAULT_IMAGE
                },
                contentScale = ContentScale.Crop,
                circularReveal = CircularReveal(),
                placeHolder = ImageBitmap.imageResource(id = R.drawable.music_logo)
            )
            Text(
                text = artist.name,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(8.dp)
            )
        }
    }

}