package com.maks.musicapp.data.dto.tracks

import com.maks.musicapp.data.dto.Headers

data class Track(
    val headers: Headers,
    val results: List<TrackResult>
)