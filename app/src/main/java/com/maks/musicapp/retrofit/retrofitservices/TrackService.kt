package com.maks.musicapp.retrofit.retrofitservices

import com.maks.musicapp.data.music.Track
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackService {
    @GET("tracks")
    fun getTrackResponse(
        @Query("client_id") clientId:String,
        @Query("name") name:String,
    ):Response<Track>
}