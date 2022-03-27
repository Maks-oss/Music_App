package com.maks.musicapp.retrofit

import com.maks.musicapp.retrofit.retrofitservices.MusicService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val BASE_URL = "https://api.jamendo.com/v3.0/"


    fun provideTrackApi(retrofit: Retrofit): MusicService = retrofit.create(
        MusicService::class.java
    )


    fun provideRetrofitAuth(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}