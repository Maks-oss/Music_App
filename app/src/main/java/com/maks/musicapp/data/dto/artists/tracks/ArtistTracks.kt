package com.maks.musicapp.data.dto.artists.tracks

import com.maks.musicapp.data.dto.Headers

data class ArtistTracks(
    val headers: Headers,
    val results: List<ArtistTracksInfo>
)