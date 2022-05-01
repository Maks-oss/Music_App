package com.maks.musicapp.repository

import com.maks.musicapp.BuildConfig
import com.maks.musicapp.data.dto.albums.AlbumResult
import com.maks.musicapp.data.dto.albums.tracks.AlbumTracksResult
import com.maks.musicapp.data.dto.artists.ArtistResult
import com.maks.musicapp.data.dto.artists.tracks.ArtistTracksResult
import com.maks.musicapp.data.dto.tracks.TrackResult
import com.maks.musicapp.retrofit.retrofitservices.MusicService

class MusicRepositoryImpl(private val musicService: MusicService) : MusicRepository {


    override suspend fun getTracksByName(name: String): List<TrackResult>? =
        musicService.getTracksResponse(BuildConfig.clientId, name).body()?.results?.filter { it.audio.isNotEmpty() }


    override suspend fun getArtistsByName(name: String): List<ArtistResult>? =
        musicService.getArtistsResponse(BuildConfig.clientId, name).body()?.results

    override suspend fun getAlbumsByName(name: String): List<AlbumResult>? =
        musicService.getAlbumsResponse(BuildConfig.clientId, name).body()?.results

    override suspend fun getArtistTracks(id: String): List<ArtistTracksResult>? =
        musicService.getArtistTracksResponse(BuildConfig.clientId, id)
            .body()?.results?.firstOrNull()?.tracks

    override suspend fun getAlbumTracks(id: String): List<AlbumTracksResult>? =
        musicService.getAlbumsTracksResponse(BuildConfig.clientId, id)
            .body()?.results?.firstOrNull()?.tracks

}