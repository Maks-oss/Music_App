package com.maks.musicapp.ui.composeutils

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maks.musicapp.data.music.track.TrackResult
import com.maks.musicapp.ui.lists.TracksList
import com.maks.musicapp.ui.viewmodels.MusicViewModel


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun TrackBottomSheetLayout(
    musicViewModel: MusicViewModel,
    bottomSheetState: ModalBottomSheetState,
    trackListItemClickAction: (TrackResult) -> Unit,
    content: @Composable () -> Unit
) {

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Box(Modifier.defaultMinSize(minHeight = 1.dp)) {
                TracksList(
                    tracksUiState = musicViewModel.tracksUiState,
                    listScrollAction = { },
                    trackListItemClickAction = trackListItemClickAction
                )
            }

        },
        content = content
    )
}
