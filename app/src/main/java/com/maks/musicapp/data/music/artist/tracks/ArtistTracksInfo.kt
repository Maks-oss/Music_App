package com.maks.musicapp.data.music.artist.tracks

data class ArtistTracksInfo(
    val id: String,
    val image: String,
    val joindate: String,
    val name: String,
    val tracks: List<ArtistTracksResult>,
    val website: String
)