package com.maks.musicapp.data.dto.artists

data class ArtistResult(
    val id: String,
    val image: String?,
    val joindate: String,
    val name: String,
    val shareurl: String,
    val shorturl: String,
    val website: String
)