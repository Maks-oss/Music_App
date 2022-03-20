package com.maks.musicapp.retrofit

import com.maks.musicapp.retrofit.retrofitservices.AuthorizationService
import com.maks.musicapp.retrofit.retrofitservices.TrackService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val REGISTRATION_URL = "https://api.jamendo.com/v3.0/"

    fun provideAuthApi(retrofit: Retrofit): AuthorizationService = retrofit.create(
        AuthorizationService::class.java
    )
    fun provideTrackApi(retrofit: Retrofit): TrackService = retrofit.create(
        TrackService::class.java
    )

//    @SuppressLint("NewApi")
//    private val httpClient = OkHttpClient.Builder().addInterceptor { chain ->
//
//        val newRequest: Request = chain.request().newBuilder()
//            .addHeader("Authorization","Bearer")
//            .build()
//        chain.proceed(newRequest)
//    }.build()

    fun provideRetrofitAuth(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(REGISTRATION_URL)
//            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}