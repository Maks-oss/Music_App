package com.maks.musicapp.data.token


data class RequestAccessToken(
    val access_token: String,
    val expires_in: Int,
    val token_type: String,
    val scope:String,
    val refresh_token:String,
)