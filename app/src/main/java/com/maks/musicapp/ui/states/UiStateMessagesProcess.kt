package com.maks.musicapp.ui.states

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.maks.musicapp.R
import com.maks.musicapp.utils.showMessage

@Composable
fun ProcessTracksUiStateMessages(
    tracksUiState: TracksUiState,
    snackbarHostState: SnackbarHostState,
    messageShown: () -> Unit
) {
    val errorMessage = tracksUiState.message
    if (!errorMessage.isNullOrEmpty()) {
        snackbarHostState.showMessage(errorMessage)
        messageShown()
    }
}

@Composable
fun ProcessArtistsUiStateMessages(
    artistsUiState: ArtistsUiState,
    snackbarHostState: SnackbarHostState,
    messageShown: () -> Unit
) {
    val errorMessage = artistsUiState.message
    if (!errorMessage.isNullOrEmpty()) {
        snackbarHostState.showMessage(errorMessage)
        messageShown()
    }
}

@Composable
fun ProcessAlbumsUiStateMessages(
    albumsUiState: AlbumsUiState,
    snackbarHostState: SnackbarHostState,
    messageShown: () -> Unit
) {
    val errorMessage = albumsUiState.message
    if (!errorMessage.isNullOrEmpty()) {
        snackbarHostState.showMessage(errorMessage)
        messageShown()
    }
}

