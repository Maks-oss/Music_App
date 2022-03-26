package com.maks.musicapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.maks.musicapp.data.music.track.TrackResult
import com.maks.musicapp.ui.composeutils.MusicTabs
import com.maks.musicapp.ui.composeutils.MusicTextField
import com.maks.musicapp.ui.lists.ArtistsList
import com.maks.musicapp.ui.lists.TracksList
import com.maks.musicapp.utils.Routes
import com.maks.musicapp.utils.TabRoutes
import com.maks.musicapp.utils.TabRowConstants
import com.maks.musicapp.viewmodels.MusicViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun MainScreen(musicViewModel: MusicViewModel, navController: NavController) {
    val musicViewModelStates = musicViewModel.musicViewModelStates
    val textValue by musicViewModelStates.searchName
    val currentJob by musicViewModelStates.currentJob
    val tabState by musicViewModelStates.tabState
    val isTextFieldVisible by musicViewModelStates.textFieldVisibility

    val scope = rememberCoroutineScope()
    val tabsList = listOf(TabRoutes.TracksTab, TabRoutes.ArtistsTab)

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
                    musicViewModel.findTracksByName()
                    musicViewModel.findArtistsByName()
                }
                musicViewModelStates.setCurrentJobValue(
                    job
                )
            }
        })

        DisplayList(tabState, musicViewModel,
            listScrollAction = { scrollState ->
                musicViewModelStates.setTextFieldVisibilityValue(scrollState.firstVisibleItemIndex == 0)
            }, cardClickAction = { trackResult->
                musicViewModel.trackDetail = trackResult
                navController.navigate(Routes.TrackDetailsScreenRoute.route)
            })
    }

}

@ExperimentalFoundationApi
@Composable
private fun DisplayList(
    tabState: Int,
    musicViewModel: MusicViewModel,
    listScrollAction: (LazyListState) -> Unit, cardClickAction: (TrackResult) -> Unit
) {
    when (tabState) {
        TabRowConstants.TRACK_TAB_INDEX -> TracksList(
            musicViewModel,
            listScrollAction = listScrollAction,
            cardClickAction = cardClickAction
        )
        TabRowConstants.ARTIST_TAB_INDEX -> ArtistsList(
            musicViewModel,
            listScrollAction = listScrollAction
        )
    }
}

