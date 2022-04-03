package com.maks.musicapp.ui.lists

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.maks.musicapp.R
import com.maks.musicapp.data.domain.Album
import com.maks.musicapp.data.dto.albums.AlbumResult
import com.maks.musicapp.ui.states.AlbumsUiState
import com.maks.musicapp.ui.animation.DisplayShimmer
import com.maks.musicapp.utils.AppConstants
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

@ExperimentalFoundationApi
@Composable
fun AlbumsList(
    albumsUiState: AlbumsUiState,
    listScrollAction: (LazyListState) -> Unit,
    albumListItemClickAction: (Album) -> Unit
) {

    val scrollState = rememberLazyListState()
    listScrollAction(scrollState)
    DisplayShimmer(albumsUiState.isLoading)
    DisplayAlbumsList(albumsUiState.albumsResult, scrollState,albumListItemClickAction)

}

@ExperimentalFoundationApi
@Composable
fun DisplayAlbumsList(
    albums: List<Album>?,
    scrollState: LazyListState,
    albumListItemClickAction: (Album) -> Unit
) {
    albums?.let { albumList ->
        LazyVerticalGrid(cells = GridCells.Fixed(2), state = scrollState) {
            items(albumList) {
                AlbumsListItem(album = it,albumListItemClickAction)
            }
        }
    }
}

@Composable
fun AlbumsListItem(album: Album, albumListItemClickAction: (Album) -> Unit) {
    Card(modifier = Modifier.padding(8.dp).clickable { albumListItemClickAction(album) }, elevation = 8.dp) {
        Column {
            GlideImage(
                imageModel = album.image.ifEmpty {
                    AppConstants.DEFAULT_IMAGE
                },
                contentScale = ContentScale.Crop,
                circularReveal = CircularReveal(),
                placeHolder = ImageBitmap.imageResource(R.drawable.music_logo),
            )
            Text(
                text = album.name,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(8.dp)
            )
        }
    }

}