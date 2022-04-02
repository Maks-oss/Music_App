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
import com.maks.musicapp.data.music.artist.ArtistResult
import com.maks.musicapp.ui.states.ArtistsUiState
import com.maks.musicapp.ui.animation.DisplayShimmer
import com.maks.musicapp.utils.AppConstants
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

@ExperimentalFoundationApi
@Composable
fun ArtistsList(
    artistsUiState: ArtistsUiState,
    listScrollAction: (LazyListState) -> Unit,
    artistListItemClickAction: (ArtistResult) -> Unit
) {
//    val artists by musicViewModel.artistListLiveData.observeAsState()
//    val isLoading by musicViewModel.musicViewModelStates.isLoading
    val scrollState = rememberLazyListState()
    listScrollAction(scrollState)
    DisplayShimmer(artistsUiState.isLoading)
    DisplayArtistsList(artistsUiState.artistsResult, scrollState, artistListItemClickAction)

}

@ExperimentalFoundationApi
@Composable
fun DisplayArtistsList(
    artists: List<ArtistResult>?,
    scrollState: LazyListState,
    artistListItemClickAction: (ArtistResult) -> Unit
) {
    artists?.let { trackList ->
        LazyVerticalGrid(cells = GridCells.Fixed(2), state = scrollState) {
            items(trackList) {
                ArtistsListItem(artistResult = it, artistListItemClickAction)
            }
        }
    }
}

@Composable
fun ArtistsListItem(artistResult: ArtistResult, artistListItemClickAction: (ArtistResult) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { artistListItemClickAction(artistResult) },
        elevation = 8.dp
    ) {
        Column {
            GlideImage(
                imageModel = artistResult.image?.ifEmpty {
                    AppConstants.DEFAULT_IMAGE
                },
                contentScale = ContentScale.Crop,
                circularReveal = CircularReveal(),
                placeHolder = ImageBitmap.imageResource(id = R.drawable.music_logo)
            )
            Text(
                text = artistResult.name,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
        }
    }

}