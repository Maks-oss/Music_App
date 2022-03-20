package com.maks.musicapp.retrofit.retrofitservices

import com.maks.musicapp.data.token.RequestAccessToken
import retrofit2.Response
import retrofit2.http.*

interface AuthorizationService {
    @FormUrlEncoded
    @POST("oauth/grant")
    suspend fun getAccessToken(
        @Field("grant_type") grantType:String,
        @Field("code") code:String,
        @Field("redirect_uri") redirectUri:String,
        @Field("client_id") clientId:String,
        @Field("client_secret") clientSecret:String,
    ): Response<RequestAccessToken>
}
