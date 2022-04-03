package com.maks.musicapp.ui.states

import androidx.annotation.StringRes
import com.maks.musicapp.data.music.albums.AlbumResult

data class AlbumsUiState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val albumsResult: List<AlbumResult>? = null
)