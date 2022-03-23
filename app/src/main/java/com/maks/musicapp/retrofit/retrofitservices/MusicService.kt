package com.maks.musicapp.retrofit.retrofitservices

import com.maks.musicapp.data.music.artist.Artists
import com.maks.musicapp.data.music.track.Track
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicService {
    @GET("tracks")
    suspend fun getTracksResponse(
        @Query("client_id") clientId:String,
        @Query("namesearch") name:String,
    ):Response<Track>

    @GET("artists")
    suspend fun getArtistsResponse(
        @Query("client_id") clientId:String,
        @Query("namesearch") name:String,
    ):Response<Artists>
}