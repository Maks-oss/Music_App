package com.maks.musicapp.retrofit.retrofitservices

import com.maks.musicapp.data.dto.albums.Albums
import com.maks.musicapp.data.dto.albums.tracks.AlbumTracks
import com.maks.musicapp.data.dto.artists.Artists
import com.maks.musicapp.data.dto.artists.tracks.ArtistTracks
import com.maks.musicapp.data.dto.feeds.Feeds
import com.maks.musicapp.data.dto.tracks.Track
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

    @GET("albums")
    suspend fun getAlbumsResponse(
        @Query("client_id") clientId:String,
        @Query("namesearch") name:String,
    ):Response<Albums>

    @GET("artists/tracks")
    suspend fun getArtistTracksResponse(
        @Query("client_id") clientId:String,
        @Query("id") id:String,
    ):Response<ArtistTracks>

    @GET("albums/tracks")
    suspend fun getAlbumsTracksResponse(
        @Query("client_id") clientId:String,
        @Query("id") id:String,
    ):Response<AlbumTracks>

    @GET("feeds")
    suspend fun getFeedsResponse(
        @Query("client_id") clientId:String,
        @Query("type") type:String,
    ):Response<Feeds>
}