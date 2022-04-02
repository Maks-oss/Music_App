package com.maks.musicapp.repository

import com.maks.musicapp.BuildConfig
import com.maks.musicapp.data.music.albums.AlbumResult
import com.maks.musicapp.data.music.artist.ArtistResult
import com.maks.musicapp.data.music.artist.tracks.ArtistTracksResult
import com.maks.musicapp.data.music.track.TrackResult
import com.maks.musicapp.retrofit.retrofitservices.MusicService
import com.maks.musicapp.utils.Result

class MusicRepositoryImpl(private val musicService: MusicService) : MusicRepository {


    override suspend fun getTracksByName(name: String): List<TrackResult>? =
       musicService.getTracksResponse(BuildConfig.clientId, name).body()?.results


    override suspend fun getArtistsByName(name: String): List<ArtistResult>? =
        musicService.getArtistsResponse(BuildConfig.clientId, name).body()?.results

    override suspend fun getAlbumsByName(name: String): List<AlbumResult>? =
        musicService.getAlbumsResponse(BuildConfig.clientId, name).body()?.results

    override suspend fun getArtistTracks(id: String): List<ArtistTracksResult>? =
        musicService.getArtistTracksResponse(BuildConfig.clientId, id)
            .body()?.results?.firstOrNull()?.tracks

}