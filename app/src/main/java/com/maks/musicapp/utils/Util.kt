package com.maks.musicapp.utils

import com.maks.musicapp.BuildConfig

fun spotifyAuthorizationUrl(): String {
    val scope = "user-read-private user-read-email"
    val state = generateRandomString()
    return "https://accounts.spotify.com/authorize?client_id=${BuildConfig.clientId}&response_type=code&scope=$scope&redirect_uri=${BuildConfig.redirectUri}&state=$state"

}

private fun generateRandomString(): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..16)
        .map { allowedChars.random() }
        .joinToString("")
}
