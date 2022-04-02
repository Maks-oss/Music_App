package com.maks.musicapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.maks.musicapp.data.music.albums.AlbumResult
import com.maks.musicapp.data.music.artist.ArtistResult
import com.maks.musicapp.data.music.track.TrackResult
import com.maks.musicapp.ui.composeutils.MusicTabs
import com.maks.musicapp.ui.composeutils.MusicTextField
import com.maks.musicapp.ui.lists.AlbumsList
import com.maks.musicapp.ui.lists.ArtistsList
import com.maks.musicapp.ui.lists.TracksList
import com.maks.musicapp.ui.states.ProcessAlbumsUiState
import com.maks.musicapp.ui.states.ProcessArtistsUiState
import com.maks.musicapp.ui.states.ProcessTracksUiState
import com.maks.musicapp.ui.viewmodels.MusicViewModel
import com.maks.musicapp.utils.Routes
import com.maks.musicapp.utils.TabRoutes
import com.maks.musicapp.utils.TabRowConstants
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun MainScreen(
    musicViewModel: MusicViewModel,
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    val musicViewModelStates = musicViewModel.musicViewModelStates
    val textValue by musicViewModelStates.searchName
    val currentJob by musicViewModelStates.currentJob
    val tabState by musicViewModelStates.tabState
    val isTextFieldVisible by musicViewModelStates.textFieldVisibility

    val scope = rememberCoroutineScope()
    val tabsList = listOf(TabRoutes.TracksTab, TabRoutes.ArtistsTab, TabRoutes.AlbumsTab)

    Column {

        MusicTabs(tabIndex = tabState, tabsList = tabsList, tabAction = {
            musicViewModelStates.setTabStateValue(it)
        })
        MusicTextField(textValue = textValue, isVisible = isTextFieldVisible, onValueChange = {
            musicViewModelStates.setSearchNameValue(it)
            currentJob?.cancel()
            if (it.isNotEmpty()) {
                val job = scope.async {
                    musicViewModelStates.setSearchNameValue(it)
                    delay(1000)
                    musicViewModel.apply {
                        findTracksByName()
                        findArtistsByName()
                        findAlbumsByName()
                    }
                }
                musicViewModelStates.setCurrentJobValue(job)
            }
        })

        DisplayList(tabState, musicViewModel,
            listScrollAction = { scrollState ->
                musicViewModelStates.setTextFieldVisibilityValue(scrollState.firstVisibleItemIndex == 0)
            }, trackItemClickAction = { trackResult ->
                musicViewModel.currentTrack = trackResult
                navController.navigate(Routes.TrackDetailsScreenRoute.route)
            }, artistItemClickAction = { artistResult ->
                musicViewModel.currentArtist = artistResult
                navController.navigate(Routes.ArtistDetailsScreenRoute.route)
            }, albumItemClickAction = { albumResult ->
                musicViewModel.currentAlbum = albumResult
            })
        ProcessListUiStates(
            musicViewModel = musicViewModel,
            snackbarHostState = snackbarHostState,
            tabIndex = tabState
        )
    }

}

@ExperimentalFoundationApi
@Composable
private fun DisplayList(
    tabState: Int,
    musicViewModel: MusicViewModel,
    listScrollAction: (LazyListState) -> Unit,
    trackItemClickAction: (TrackResult) -> Unit,
    artistItemClickAction: (ArtistResult) -> Unit,
    albumItemClickAction: (AlbumResult) -> Unit,
) {
    when (tabState) {
        TabRowConstants.TRACK_TAB_INDEX -> TracksList(
            tracksUiState = musicViewModel.tracksUiState,
            listScrollAction = listScrollAction,
            trackListItemClickAction = trackItemClickAction
        )
        TabRowConstants.ARTIST_TAB_INDEX -> ArtistsList(
            artistsUiState = musicViewModel.artistsUiState,
            listScrollAction = listScrollAction,
            artistListItemClickAction = artistItemClickAction
        )
        TabRowConstants.ALBUM_TAB_INDEX -> AlbumsList(
            albumsUiState = musicViewModel.albumsUiState,
            listScrollAction = listScrollAction,
            albumListItemClickAction = albumItemClickAction
        )
    }
}

@Composable
private fun ProcessListUiStates(
    musicViewModel: MusicViewModel,
    snackbarHostState: SnackbarHostState,
    tabIndex: Int
) {
    when (tabIndex) {
        TabRowConstants.TRACK_TAB_INDEX -> ProcessTracksUiState(
            tracksUiState = musicViewModel.tracksUiState,
            snackbarHostState = snackbarHostState,
            messageShown = { musicViewModel.tracksMessageDisplayed() }
        )
        TabRowConstants.ARTIST_TAB_INDEX -> ProcessArtistsUiState(
            artistsUiState = musicViewModel.artistsUiState,
            snackbarHostState = snackbarHostState,
            messageShown = { musicViewModel.artistsMessageDisplayed() }
        )
        TabRowConstants.ALBUM_TAB_INDEX -> ProcessAlbumsUiState(
            albumsUiState = musicViewModel.albumsUiState,
            snackbarHostState = snackbarHostState,
            messageShown = { musicViewModel.albumsMessageDisplayed() }
        )
    }


}


