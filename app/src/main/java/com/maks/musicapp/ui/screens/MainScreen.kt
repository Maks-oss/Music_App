package com.maks.musicapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavController
import com.maks.musicapp.data.domain.Album
import com.maks.musicapp.data.domain.Artist
import com.maks.musicapp.data.domain.Track
import com.maks.musicapp.ui.composeutils.MusicTabs
import com.maks.musicapp.ui.composeutils.MusicTextField
import com.maks.musicapp.ui.lists.AlbumsList
import com.maks.musicapp.ui.lists.ArtistsList
import com.maks.musicapp.ui.lists.TracksList
import com.maks.musicapp.ui.states.ProcessUiStateMessages
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
    val focusManager = LocalFocusManager.current

    val scope = rememberCoroutineScope()
    val tabsList = listOf(TabRoutes.TracksTab, TabRoutes.ArtistsTab, TabRoutes.AlbumsTab)

    Column {
        MusicTabs(tabIndex = musicViewModelStates.tabState, tabsList = tabsList, tabAction = {
            musicViewModel.setTabStateValue(it)
        })
        MusicTextField(
            textValue = musicViewModelStates.searchInput,
            isVisible = musicViewModelStates.textFieldVisibility,
            onValueChange = {
                musicViewModel.setSearchInputValue(it)
                musicViewModelStates.currentJob?.cancel()
                if (it.isNotEmpty()) {
                    val job = scope.async {
                        musicViewModel.setSearchInputValue(it)
                        delay(1000)
                        musicViewModel.apply {
                            findTracksByName()
                            findArtistsByName()
                            findAlbumsByName()
                        }
                    }
                    musicViewModel.setCurrentJobValue(job)
                }
            })

        DisplayList(musicViewModelStates.tabState, musicViewModel,
            listScrollAction = { scrollState ->
                musicViewModel.setTextFieldVisibilityValue(scrollState.firstVisibleItemIndex == 0)
            }, trackItemClickAction = { trackResult ->
                musicViewModel.currentTrack = trackResult
                focusManager.clearFocus()
                navController.navigate(Routes.TrackDetailsScreenRoute.route)
            }, artistItemClickAction = { artistResult ->
                musicViewModel.currentArtist = artistResult
                focusManager.clearFocus()
                navController.navigate(Routes.ArtistDetailsScreenRoute.route)
            }, albumItemClickAction = { albumResult ->
                musicViewModel.currentAlbum = albumResult
                focusManager.clearFocus()
                navController.navigate(Routes.AlbumDetailsScreenRoute.route)
            })
        ProcessListUiStateMessages(
            musicViewModel = musicViewModel,
            snackbarHostState = snackbarHostState,
            tabIndex = musicViewModelStates.tabState
        )
    }

}

@ExperimentalFoundationApi
@Composable
private fun DisplayList(
    tabState: Int,
    musicViewModel: MusicViewModel,
    listScrollAction: (LazyGridState) -> Unit,
    trackItemClickAction: (Track) -> Unit,
    artistItemClickAction: (Artist) -> Unit,
    albumItemClickAction: (Album) -> Unit,
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
private fun ProcessListUiStateMessages(
    musicViewModel: MusicViewModel,
    snackbarHostState: SnackbarHostState,
    tabIndex: Int
) {
    when (tabIndex) {
        TabRowConstants.TRACK_TAB_INDEX -> ProcessUiStateMessages(
            uiState = musicViewModel.tracksUiState,
            snackbarHostState = snackbarHostState,
            messageShown = { musicViewModel.tracksMessageDisplayed() }
        )
        TabRowConstants.ARTIST_TAB_INDEX -> ProcessUiStateMessages(
            uiState = musicViewModel.artistsUiState,
            snackbarHostState = snackbarHostState,
            messageShown = { musicViewModel.artistsMessageDisplayed() }
        )
        TabRowConstants.ALBUM_TAB_INDEX -> ProcessUiStateMessages(
            uiState = musicViewModel.albumsUiState,
            snackbarHostState = snackbarHostState,
            messageShown = { musicViewModel.albumsMessageDisplayed() }
        )
    }


}


