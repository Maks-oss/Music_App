package com.maks.musicapp.data.dto.artists.tracks

data class ArtistTracksInfo(
    val id: String,
    val image: String,
    val joindate: String,
    val name: String,
    val tracks: List<ArtistTracksResult>,
    val website: String
)