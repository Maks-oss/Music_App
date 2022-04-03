package com.maks.musicapp.ui.states

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.maks.musicapp.R

@Composable
fun ProcessTracksUiStateMessages(
    tracksUiState: TracksUiState,
    snackbarHostState: SnackbarHostState,
    messageShown: () -> Unit
) {
    val errorMessage = tracksUiState.message
    val okActionLabel = stringResource(id = R.string.ok)
    if (!errorMessage.isNullOrEmpty()) {
        LaunchedEffect(errorMessage, snackbarHostState) {
            snackbarHostState.showSnackbar(errorMessage, actionLabel = okActionLabel)
            messageShown()
        }
    }
}

@Composable
fun ProcessArtistsUiStateMessages(
    artistsUiState: ArtistsUiState,
    snackbarHostState: SnackbarHostState,
    messageShown: () -> Unit
) {
    val errorMessage = artistsUiState.message
    val okActionLabel = stringResource(id = R.string.ok)
    if (!errorMessage.isNullOrEmpty()) {
        LaunchedEffect(errorMessage, snackbarHostState) {
            snackbarHostState.showSnackbar(errorMessage,okActionLabel)
            messageShown()
        }
    }
}

@Composable
fun ProcessAlbumsUiStateMessages(
    albumsUiState: AlbumsUiState,
    snackbarHostState: SnackbarHostState,
    messageShown: () -> Unit
) {
    val errorMessage = albumsUiState.message
    val okActionLabel = stringResource(id = R.string.ok)
    if (!errorMessage.isNullOrEmpty()) {
        LaunchedEffect(errorMessage, snackbarHostState) {
            snackbarHostState.showSnackbar(errorMessage,okActionLabel)
            messageShown()
        }
    }
}
