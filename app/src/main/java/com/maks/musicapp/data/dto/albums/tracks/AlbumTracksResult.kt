package com.maks.musicapp.data.dto.albums.tracks

data class AlbumTracksResult(
    val audio: String,
    val audiodownload: String,
    val audiodownload_allowed: Boolean,
    val duration: String,
    val id: String,
    val license_ccurl: String,
    val name: String,
    val position: String
)