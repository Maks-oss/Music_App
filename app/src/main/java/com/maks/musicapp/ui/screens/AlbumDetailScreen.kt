package com.maks.musicapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.maks.musicapp.R
import com.maks.musicapp.data.domain.Album
import com.maks.musicapp.ui.composeutils.CustomOutlinedButton
import com.maks.musicapp.ui.composeutils.TrackBottomSheetLayout
import com.maks.musicapp.ui.states.ProcessTracksUiStateMessages
import com.maks.musicapp.ui.viewmodels.MusicViewModel
import com.maks.musicapp.utils.Routes
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun AlbumDetailScreen(
    album: Album,
    musicViewModel: MusicViewModel,
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val coroutineScope = rememberCoroutineScope()
    TrackBottomSheetLayout(
        tracksUiState = musicViewModel.albumTracksUiState,
        bottomSheetState = bottomSheetState,
        trackListItemClickAction = { track ->
            musicViewModel.currentTrack = track
            navController.navigate(Routes.TrackDetailsScreenRoute.route)
        }) {
        DisplayAlbumDetail(album, showTracksClickAction = {
            musicViewModel.findAlbumsTracks()
            coroutineScope.launch {
                bottomSheetState.show()
            }
        })
    }
    ProcessTracksUiStateMessages(
        tracksUiState = musicViewModel.albumTracksUiState,
        snackbarHostState = snackbarHostState,
        messageShown = {
            coroutineScope.launch {
                bottomSheetState.hide()
            }
            musicViewModel.albumTracksMessageDisplayed()
        }
    )

}

@Composable
private fun DisplayAlbumDetail(album: Album, showTracksClickAction: () -> Unit) {
    Surface(
        elevation = 8.dp,
        modifier = Modifier.padding(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            GlideImage(
                imageModel = album.image,
                circularReveal = CircularReveal(),
                contentScale = ContentScale.Crop,
                placeHolder = ImageBitmap.imageResource(id = R.drawable.music_logo)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(text = album.name, style = MaterialTheme.typography.body2)
                Text(text = album.releasedate)
            }
            CustomOutlinedButton(
                modifier = Modifier.padding(8.dp),
                text = "Show Album Tracks",
                onClick = showTracksClickAction
            )

        }
    }
}

