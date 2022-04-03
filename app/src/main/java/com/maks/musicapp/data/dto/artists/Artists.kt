package com.maks.musicapp.data.dto.artists

import com.maks.musicapp.data.dto.Headers

data class Artists(
    val headers: Headers,
    val results: List<ArtistResult>
)