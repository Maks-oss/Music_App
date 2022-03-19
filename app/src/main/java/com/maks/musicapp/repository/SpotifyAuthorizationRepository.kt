package com.maks.musicapp.repository

import com.maks.musicapp.data.RequestAccessToken

interface SpotifyAuthorizationRepository {
    suspend fun getAuthorizationToken(code:String):RequestAccessToken
}