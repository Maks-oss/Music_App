package com.maks.musicapp.data.dto.albums.tracks

import com.maks.musicapp.data.dto.Headers

data class AlbumTracks(
    val headers: Headers,
    val results: List<AlbumTracksInfo>
)