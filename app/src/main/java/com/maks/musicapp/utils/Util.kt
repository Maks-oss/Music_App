package com.maks.musicapp.utils

import com.maks.musicapp.BuildConfig

fun spotifyAuthorizationUrl(): String {
    val state = generateRandomString()
    return "https://api.jamendo.com/v3.0/oauth/authorize?client_id=${BuildConfig.clientId}&redirect_uri=${BuildConfig.redirectUri}&state=$state"

}

private fun generateRandomString(): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..16)
        .map { allowedChars.random() }
        .joinToString("")
}
