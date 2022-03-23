package com.maks.musicapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import com.maks.musicapp.ui.composeutils.MusicTabs
import com.maks.musicapp.ui.composeutils.MusicTextField
import com.maks.musicapp.ui.lists.ArtistsList
import com.maks.musicapp.ui.lists.TracksList
import com.maks.musicapp.utils.TabRoutes
import com.maks.musicapp.utils.TabRowConstants
import com.maks.musicapp.viewmodels.MusicViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun MainScreen(musicViewModel: MusicViewModel) {

    val textValue by musicViewModel.searchName
    val scope = rememberCoroutineScope()
    var currentJob by remember { mutableStateOf<Job?>(null) }
    var tabState by remember { mutableStateOf(0) }
    val tabsList = listOf(TabRoutes.TracksTab, TabRoutes.ArtistsTab)

    Column {
        MusicTabs(tabIndex = tabState, tabsList = tabsList, tabAction = {
            tabState = it
        })
        MusicTextField(textValue = textValue, onValueChange = {
            musicViewModel.setSearchNameValue(it)
            currentJob?.cancel()
            if (it.isNotEmpty()) {
                currentJob = scope.async {
                    musicViewModel.setSearchNameValue(it)
                    delay(1000)
                    musicViewModel.findTracksByName()
                    musicViewModel.findArtistsByName()
                }
            }
        })
        val listScrollAction: () -> Unit = {

        }
        when (tabState) {
            TabRowConstants.TRACK_TAB_INDEX -> TracksList(
                musicViewModel,
                listScrollAction = listScrollAction
            )
            TabRowConstants.ARTIST_TAB_INDEX -> ArtistsList(
                musicViewModel,
                listScrollAction = listScrollAction
            )
        }
    }

}

