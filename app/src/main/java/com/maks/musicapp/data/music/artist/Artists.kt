package com.maks.musicapp.data.music.artist

import com.maks.musicapp.data.music.Headers

data class Artists(
    val headers: Headers,
    val results: List<ArtistResult>
)