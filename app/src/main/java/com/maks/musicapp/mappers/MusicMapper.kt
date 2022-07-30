package com.maks.musicapp.mappers

import com.maks.musicapp.data.domain.Album
import com.maks.musicapp.data.domain.Artist
import com.maks.musicapp.data.domain.Track
import com.maks.musicapp.data.dto.albums.AlbumResult
import com.maks.musicapp.data.dto.albums.tracks.AlbumTracksResult
import com.maks.musicapp.data.dto.artists.ArtistResult
import com.maks.musicapp.data.dto.artists.tracks.ArtistTracksResult
import com.maks.musicapp.data.dto.tracks.TrackResult

class MusicMapper {
    fun toAlbumList(albumResult: List<AlbumResult>): List<Album> {
        return albumResult.map { result ->
            Album(
                artist_id = result.artist_id,
                artist_name = result.artist_name,
                id = result.id,
                image = result.image,
                name = result.name,
                releasedate = result.releasedate,
                zip = result.zip
            )
        }
    }

    fun toTrackList(trackResult: List<TrackResult>,query:String): List<Track> {

        return trackResult.map { result ->
            Track(
                album_id = result.album_id,
                album_name = result.album_name,
                artist_id = result.artist_id,
                artist_name = result.artist_name,
                audio = result.audio,
                audiodownload = result.audiodownload,
                duration = result.duration,
                id = result.id,
                image = result.image,
                musicinfo = result.musicinfo,
                name = result.name,
                releasedate = result.releasedate,
                searchQuery = query
            )
        }
    }

    fun toArtistTracksList(
        artist: Artist,
        artistTrackResult: List<ArtistTracksResult>
    ): List<Track> {
        return artistTrackResult.map { result ->
            Track(
                album_id = result.album_id,
                album_name = result.album_name,
                artist_id = artist.id,
                artist_name = artist.name,
                audio = result.audio,
                duration = runCatching {  result.duration.toInt()}.getOrDefault(0),
                audiodownload = result.audiodownload,
                id = result.id,
                image = result.image,
                musicinfo = null,
                name = result.name,
                releasedate = result.releasedate
            )
        }
    }

    fun toAlbumTracksList(
        album: Album,
        albumTracksResult: List<AlbumTracksResult>
    ): List<Track> {
        return albumTracksResult.map { result ->
            Track(
                album_id = album.id,
                album_name = album.name,
                artist_id = album.artist_id,
                artist_name = album.artist_name,
                audio = result.audio,
                duration = runCatching {  result.duration.toInt()}.getOrDefault(0),
                audiodownload = result.audiodownload,
                id = result.id,
                image = album.image,
                musicinfo = null,
                name = result.name,
                releasedate = album.releasedate
            )
        }
    }

    fun toArtistList(artistResult: List<ArtistResult>): List<Artist> {
        return artistResult.map { result ->
            Artist(
                id = result.id,
                image = result.image,
                name = result.name,
                website = result.website
            )
        }
    }
}