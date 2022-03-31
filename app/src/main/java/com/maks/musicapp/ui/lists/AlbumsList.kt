package com.maks.musicapp.ui.lists

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.maks.musicapp.data.music.albums.AlbumResult
import com.maks.musicapp.ui.animation.DisplayShimmer
import com.maks.musicapp.utils.AppConstants
import com.maks.musicapp.utils.Resource
import com.maks.musicapp.viewmodels.MusicViewModel
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

@ExperimentalFoundationApi
@Composable
fun AlbumsList(
    musicViewModel: MusicViewModel,
    listScrollAction: (LazyListState) -> Unit,
    albumListItemClickAction: (AlbumResult) -> Unit
) {
    val albums by musicViewModel.albumsListLiveData.observeAsState()
    val isLoading by musicViewModel.musicViewModelStates.isLoading
    val scrollState = rememberLazyListState()
    listScrollAction(scrollState)
    DisplayShimmer(isLoading)
    DisplayAlbumsList(albums, scrollState,albumListItemClickAction)

}

@ExperimentalFoundationApi
@Composable
fun DisplayAlbumsList(
    artists: Resource<List<AlbumResult>>?,
    scrollState: LazyListState,
    albumListItemClickAction: (AlbumResult) -> Unit
) {
    artists?.value?.let { trackList ->
        LazyVerticalGrid(cells = GridCells.Fixed(2), state = scrollState) {
            items(trackList) {
                AlbumsListItem(albumResult = it,albumListItemClickAction)
            }
        }
    }
}

@Composable
fun AlbumsListItem(albumResult: AlbumResult,albumListItemClickAction: (AlbumResult) -> Unit) {
    Card(modifier = Modifier.padding(8.dp).clickable { albumListItemClickAction(albumResult) }, elevation = 8.dp) {
        Column {
            GlideImage(
                imageModel = albumResult.image.ifEmpty {
                    AppConstants.DEFAULT_IMAGE
                },
                contentScale = ContentScale.Crop,
                circularReveal = CircularReveal(),
//                placeHolder = ImageBitmap.imageResource(R.drawable.music_background),
            )
            Text(
                text = albumResult.name,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
        }
    }

}