package com.maks.musicapp.repository

import com.maks.musicapp.data.dto.albums.AlbumResult
import com.maks.musicapp.data.dto.artists.ArtistResult
import com.maks.musicapp.data.dto.artists.tracks.ArtistTracksResult
import com.maks.musicapp.data.dto.tracks.TrackResult

interface MusicRepository {
    suspend fun getTracksByName(name: String): List<TrackResult>?

    suspend fun getArtistsByName(name: String): List<ArtistResult>?

    suspend fun getAlbumsByName(name: String): List<AlbumResult>?

    suspend fun getArtistTracks(id: String): List<ArtistTracksResult>?
}