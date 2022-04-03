package com.maks.musicapp.ui.states

import androidx.annotation.StringRes
import com.maks.musicapp.data.music.track.TrackResult

data class TracksUiState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val tracksResult: List<TrackResult>? = null
)