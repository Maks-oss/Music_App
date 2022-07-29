package com.maks.musicapp.data.domain

import com.maks.musicapp.data.dto.tracks.Musicinfo
import java.io.Serializable

data class Track(
    val album_id: String? = null,
    val album_name: String? = null,
    val artist_id: String? = null,
    val artist_name: String? = null,
    val audio: String? = null,
    val audiodownload: String? = null,
    val duration: Int? = null,
    val id: String? = null,
    val image: String? = null,
    val musicinfo: Musicinfo? = null,
    val name: String? = null,
    val releasedate: String? = null
): Serializable