package com.maks.musicapp.retrofit

import com.maks.musicapp.retrofit.retrofitservices.AuthorizationService
import com.maks.musicapp.retrofit.retrofitservices.MusicService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val REGISTRATION_URL = "https://api.jamendo.com/v3.0/"

    fun provideAuthApi(retrofit: Retrofit): AuthorizationService = retrofit.create(
        AuthorizationService::class.java
    )
    fun provideTrackApi(retrofit: Retrofit): MusicService = retrofit.create(
        MusicService::class.java
    )


    fun provideRetrofitAuth(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(REGISTRATION_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}