package com.maks.musicapp.data.domain

import com.maks.musicapp.data.dto.tracks.Musicinfo

data class Track(
    val album_id: String,
    val album_name: String,
    val artist_id: String,
    val artist_name: String,
    val audio: String,
    val audiodownload: String,
    val duration: Int,
    val id: String,
    val image: String?,
    val musicinfo: Musicinfo?,
    val name: String,
    val releasedate: String
)