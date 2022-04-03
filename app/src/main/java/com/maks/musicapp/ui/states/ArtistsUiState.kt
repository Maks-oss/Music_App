package com.maks.musicapp.ui.states

import com.maks.musicapp.data.domain.Artist
import com.maks.musicapp.data.dto.artists.ArtistResult

data class ArtistsUiState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val artistsResult: List<Artist>? = null
)