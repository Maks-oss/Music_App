package com.maks.musicapp.repository

import com.maks.musicapp.data.token.RequestAccessToken

interface AuthorizationRepository {
    suspend fun getAuthorizationToken(code:String): RequestAccessToken
}