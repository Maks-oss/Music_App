package com.maks.musicapp.data.dto.artists.tracks

data class ArtistTracksResult(
    val album_id: String,
    val album_image: String,
    val album_name: String,
    val audio: String,
    val audiodownload: String,
    val audiodownload_allowed: Boolean,
    val duration: String,
    val id: String,
    val image: String,
    val license_ccurl: String,
    val name: String,
    val releasedate: String
)