package com.maks.musicapp.data.music.artist.tracks

import com.maks.musicapp.data.music.Headers

data class ArtistTracks(
    val headers: Headers,
    val results: List<ArtistTracksInfo>
)