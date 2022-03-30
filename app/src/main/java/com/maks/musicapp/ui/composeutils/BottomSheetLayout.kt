package com.maks.musicapp.ui.composeutils

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maks.musicapp.data.music.track.TrackResult
import com.maks.musicapp.ui.animation.DisplayShimmer
import com.maks.musicapp.ui.lists.TracksList
import com.maks.musicapp.viewmodels.MusicViewModel


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun TrackBottomSheetLayout(
    musicViewModel: MusicViewModel,
    bottomSheetState: ModalBottomSheetState,
    trackListItemClickAction: (TrackResult) -> Unit,
    content: @Composable () -> Unit
) {

    val isTracksLoading by musicViewModel.musicViewModelStates.isLoading
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Box(Modifier.defaultMinSize(minHeight = 1.dp)) {
                TracksList(
                    isLoading = isTracksLoading,
                    tracksLiveData = musicViewModel.artistTracksListLiveData,
                    listScrollAction = { },
                    trackListItemClickAction = trackListItemClickAction
                )
            }

        },
        content = content
    )
}
