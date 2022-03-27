package com.maks.musicapp.repository

import com.maks.musicapp.data.music.albums.AlbumResult
import com.maks.musicapp.data.music.artist.ArtistResult
import com.maks.musicapp.data.music.track.TrackResult

interface MusicRepository {
    suspend fun getTracksByName(name: String): List<TrackResult>?

    suspend fun getArtistsByName(name: String): List<ArtistResult>?

    suspend fun getAlbumsByName(name: String): List<AlbumResult>?
}