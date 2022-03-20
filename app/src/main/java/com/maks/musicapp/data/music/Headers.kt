package com.maks.musicapp.data.music

data class Headers(
    val code: Int,
    val error_message: String,
    val results_count: Int,
    val status: String,
    val warnings: String
)