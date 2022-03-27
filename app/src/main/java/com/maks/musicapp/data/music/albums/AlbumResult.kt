package com.maks.musicapp.data.music.albums

data class AlbumResult(
    val artist_id: String,
    val artist_name: String,
    val id: String,
    val image: String,
    val name: String,
    val releasedate: String,
    val shareurl: String,
    val shorturl: String,
    val zip: String,
    val zip_allowed: Boolean
)