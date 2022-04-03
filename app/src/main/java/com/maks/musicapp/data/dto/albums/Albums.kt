package com.maks.musicapp.data.dto.albums

import com.maks.musicapp.data.dto.Headers

data class Albums(
    val headers: Headers,
    val results: List<AlbumResult>
)