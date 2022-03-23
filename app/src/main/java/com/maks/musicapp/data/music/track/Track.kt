package com.maks.musicapp.data.music.track

import com.maks.musicapp.data.music.Headers

data class Track(
    val headers: Headers,
    val results: List<TrackResult>
)