package com.maks.musicapp.ui.states

import com.maks.musicapp.data.domain.Album
import com.maks.musicapp.data.dto.albums.AlbumResult

data class AlbumsUiState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val albumsResult: List<Album>? = null
)