package com.maks.musicapp.ui.states

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun ProcessTracksUiState(tracksUiState: TracksUiState, snackbarHostState: SnackbarHostState,messageShown:()->Unit) {
    val errorMessage = tracksUiState.message
    if (!errorMessage.isNullOrEmpty()) {
        LaunchedEffect(errorMessage,snackbarHostState){
            snackbarHostState.showSnackbar(errorMessage)
            messageShown()
        }
    }
}
@Composable
fun ProcessArtistsUiState(artistsUiState: ArtistsUiState, snackbarHostState: SnackbarHostState,messageShown:()->Unit) {
    val errorMessage = artistsUiState.message
    if (!errorMessage.isNullOrEmpty()) {
        LaunchedEffect(errorMessage,snackbarHostState){
            snackbarHostState.showSnackbar(errorMessage)
            messageShown()
        }
    }
}
@Composable
fun ProcessAlbumsUiState(albumsUiState: AlbumsUiState, snackbarHostState: SnackbarHostState,messageShown:()->Unit) {
    val errorMessage = albumsUiState.message
    if (!errorMessage.isNullOrEmpty()) {
        LaunchedEffect(errorMessage,snackbarHostState){
            snackbarHostState.showSnackbar(errorMessage)
            messageShown()
        }
    }
}