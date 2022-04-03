package com.maks.musicapp.data.dto.albums.tracks

data class AlbumTracksInfo(
    val artist_id: String,
    val artist_name: String,
    val id: String,
    val image: String,
    val name: String,
    val releasedate: String,
    val tracks: List<AlbumTracksResult>,
    val zip: String,
    val zip_allowed: Boolean
)