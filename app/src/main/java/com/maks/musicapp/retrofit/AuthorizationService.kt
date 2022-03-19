package com.maks.musicapp.retrofit

import com.maks.musicapp.data.RequestAccessToken
import com.maks.musicapp.data.RequestAccessTokenData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthorizationService {
    @Headers("Accept: application/json")
    @POST("api/token")
    suspend fun getAccessToken(
        @Query("grant_type") grantType:String,
        @Query("code") code:String,
        @Query("redirect_uri") redirectUri:String
    ): Response<RequestAccessToken>
}
