package com.maks.musicapp.data

data class RequestAccessToken(
    val access_token: String,
    val expires_in: Int,
    val refresh_token: String,
    val scope: String,
    val token_type: String
)