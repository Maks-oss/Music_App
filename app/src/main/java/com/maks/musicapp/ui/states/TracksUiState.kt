package com.maks.musicapp.ui.states

import com.maks.musicapp.data.domain.Track
import com.maks.musicapp.data.dto.tracks.TrackResult

data class TracksUiState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val tracksResult: List<Track>? = null
)