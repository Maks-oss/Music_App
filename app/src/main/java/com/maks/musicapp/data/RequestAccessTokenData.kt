package com.maks.musicapp.data

data class RequestAccessTokenData(
    val grant_type:String,
    val code:String,
    val redirect_uri:String,
)