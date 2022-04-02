package com.maks.musicapp.ui.states

import androidx.annotation.StringRes
import com.maks.musicapp.data.music.artist.ArtistResult
import com.maks.musicapp.utils.Result

data class ArtistsUiState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val artistsResult: List<ArtistResult>? = null
)