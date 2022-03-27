package com.maks.musicapp.data.music.albums

import com.maks.musicapp.data.music.Headers

data class Albums(
    val headers: Headers,
    val results: List<AlbumResult>
)