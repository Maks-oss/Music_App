package com.maks.musicapp.repository

import android.util.Log
import com.maks.musicapp.BuildConfig
import com.maks.musicapp.data.RequestAccessToken
import com.maks.musicapp.retrofit.AuthorizationService

class AuthorizationRepositoryImpl(private val authorizationService: AuthorizationService) :
    AuthorizationRepository {
    override suspend fun getAuthorizationToken(code:String): RequestAccessToken =
        authorizationService
            .getAccessToken(
                "authorization_code",
                code,
                BuildConfig.redirectUri,
                BuildConfig.clientId,
                BuildConfig.clientSecret
            ).also {
                Log.d("TAG", "getAuthorizationToken: ${it.body()}   ${it.raw().request().url()}")
            }.body()!!
}