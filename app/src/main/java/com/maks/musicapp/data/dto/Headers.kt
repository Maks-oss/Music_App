package com.maks.musicapp.data.dto

data class Headers(
    val code: Int,
    val error_message: String,
    val results_count: Int,
    val status: String,
    val warnings: String
)