package com.maks.musicapp.ui.states

import androidx.annotation.StringRes
import com.maks.musicapp.data.music.artist.ArtistResult

data class ArtistsUiState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val artistsResult: List<ArtistResult>? = null
)