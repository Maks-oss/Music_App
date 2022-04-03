package com.maks.musicapp.ui.composeutils

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maks.musicapp.data.domain.Track
import com.maks.musicapp.data.dto.tracks.TrackResult
import com.maks.musicapp.ui.lists.TracksList
import com.maks.musicapp.ui.states.TracksUiState
import com.maks.musicapp.ui.viewmodels.MusicViewModel


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun TrackBottomSheetLayout(
    tracksUiState: TracksUiState,
    bottomSheetState: ModalBottomSheetState,
    trackListItemClickAction: (Track) -> Unit,
    content: @Composable () -> Unit
) {

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Box(Modifier.defaultMinSize(minHeight = 1.dp)) {
                TracksList(
                    tracksUiState = tracksUiState,
                    listScrollAction = { },
                    trackListItemClickAction = trackListItemClickAction
                )
            }

        },
        content = content
    )
}

