package com.maks.musicapp.retrofit

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.maks.musicapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


object RetrofitClient {
    private const val SPOTIFY_REGISTRATION_URL = "https://api.jamendo.com/v3.0/"

    fun provideSpotifyAuthApi(retrofit: Retrofit): AuthorizationService = retrofit.create(
        AuthorizationService::class.java
    )

//    @SuppressLint("NewApi")
//    private val httpClient = OkHttpClient.Builder().addInterceptor { chain ->
//        val login = Base64.getEncoder()
//            .encode("${BuildConfig.clientId}:${BuildConfig.clientSecret}".toByteArray())
//        val newRequest: Request = chain.request().newBuilder()
//            .addHeader("Authorization", login.toString())
//            .addHeader("Content-Type","application/x-www-form-urlencoded")
//            .build()
//        chain.proceed(newRequest)
//    }.build()

    fun provideRetrofitSpotifyAuth(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(SPOTIFY_REGISTRATION_URL)
//            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}