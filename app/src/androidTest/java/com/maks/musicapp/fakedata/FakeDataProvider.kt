package com.maks.musicapp.fakedata

import com.maks.musicapp.data.domain.Album
import com.maks.musicapp.data.domain.Artist
import com.maks.musicapp.data.domain.Feed
import com.maks.musicapp.data.domain.Track
import com.maks.musicapp.data.dto.albums.AlbumResult
import com.maks.musicapp.data.dto.albums.tracks.AlbumTracksResult
import com.maks.musicapp.data.dto.artists.ArtistResult
import com.maks.musicapp.data.dto.artists.tracks.ArtistTracksResult
import com.maks.musicapp.data.dto.tracks.TrackResult
import com.maks.musicapp.utils.AppConstants
import kotlinx.coroutines.delay

object FakeDataProvider {
    fun provideFakeTrackResultList(): List<TrackResult> {
        return listOf(
            TrackResult(
                "",
                AppConstants.DEFAULT_IMAGE,
                "fake name",
                "",
                "",
                "fake artist",
                "https://prod-1.storage.jamendo.com//?trackid=1532771&format=mp31&from=app-devsite",
                "",
                false,
                0,
                "1",
                "",
                "",
                null,
                "fake track",
                0,
                "",
                "",
                "",
                "",
                ""
            ),
            TrackResult(
                "",
                AppConstants.DEFAULT_IMAGE,
                "fake name2",
                "",
                "",
                "fake artist2",
                "",
                "",
                false,
                0,
                "2",
                "",
                "",
                null,
                "fake track2",
                0,
                "",
                "",
                "",
                "",
                ""
            ),
            TrackResult(
                "",
                AppConstants.DEFAULT_IMAGE,
                "fake name3",
                "",
                "",
                "fake artist3",
                "",
                "",
                false,
                0,
                "3",
                "",
                "",
                null,
                "fake track3",
                0,
                "",
                "",
                "",
                "",
                ""
            ),
        )
    }

    fun provideFakeArtistTrackResultList(): List<ArtistTracksResult> {
        return listOf(
            ArtistTracksResult(
                "",
                AppConstants.DEFAULT_IMAGE,
                "fake album",
                "https://prod-1.storage.jamendo.com//?trackid=1532771&format=mp31&from=app-devsite",
                "",
                false,
                "",
                "",
                AppConstants.DEFAULT_IMAGE,
                "",
                "fake name",
                ""
            ),
            ArtistTracksResult(
                "",
                AppConstants.DEFAULT_IMAGE,
                "fake album2",
                "",
                "",
                false,
                "",
                "",
                "2",
                "",
                "fake name2",
                ""
            ),
            ArtistTracksResult(
                "",
                AppConstants.DEFAULT_IMAGE,
                "fake album3",
                "",
                "",
                false,
                "",
                "",
                "3",
                "",
                "fake name3",
                ""
            ),
        )
    }

    fun provideFakeAlbumTracksResultList(): List<AlbumTracksResult> {
        return listOf(
            AlbumTracksResult(
                "https://prod-1.storage.jamendo.com//?trackid=1532771&format=mp31&from=app-devsite",
                "",
                true,
                "",
                "1",
                "",
                "fake name",
                ""
            ),
            AlbumTracksResult("", "", true, "", "", "2", "fake name2", ""),
            AlbumTracksResult("", "", true, "", "", "3", "fake name3", ""),
        )
    }

    fun provideFakeAlbumResultList(): List<AlbumResult> {
        return listOf(
            AlbumResult(
                "1",
                "fake name",
                "1",
                AppConstants.DEFAULT_IMAGE,
                "fake album",
                "",
                "",
                "",
                "",
                false
            ),
            AlbumResult(
                "2",
                "fake name2",
                "2",
                AppConstants.DEFAULT_IMAGE,
                "fake album2",
                "",
                "",
                "",
                "",
                false
            ),
            AlbumResult(
                "3",
                "fake name3",
                "3",
                AppConstants.DEFAULT_IMAGE,
                "fake album3",
                "",
                "",
                "",
                "",
                false
            ),
        )
    }

    fun provideFakeArtistResultList(): List<ArtistResult> {
        return listOf(
            ArtistResult("1", AppConstants.DEFAULT_IMAGE, "", "fake artist", "", "", ""),
            ArtistResult("2", AppConstants.DEFAULT_IMAGE, "", "fake artist2", "", "", ""),
            ArtistResult("3", AppConstants.DEFAULT_IMAGE, "", "fake artist3", "", "", ""),
        )
    }


    fun provideFakeFeedsList(isRefresh: Boolean = false): List<Feed> {
        return if (!isRefresh) {
            listOf(
                Feed("", "fake title", "fake text", "artist"),
                Feed("", "fake title2", "fake text2", "artist"),
                Feed("", "fake title3", "fake text3", "news"),
                Feed("", "fake title3", "fake text3", "news"),
                Feed("", "fake title3", "fake text3", "news"),
                Feed("", "fake title3", "fake text3", "news"),
            )
        } else {
            listOf(
                Feed("", "fake refresh title", "fake refresh text", "artist"),
                Feed("", "fake refresh title2", "fake refresh text2", "artist"),
                Feed("", "fake refresh title3", "fake refresh text3", "news"),
            )
        }
    }

    fun provideFakeTrack(): Track {
        return Track(
            album_id = "",
            album_name = "fake album",
            artist_id = "",
            artist_name = "fake artist",
            audio = "https://prod-1.storage.jamendo.com//?trackid=1532771&format=mp31&from=app-devsite",
            audiodownload = "",
            duration = 54,
            id = "",
            image = AppConstants.DEFAULT_IMAGE,
            musicinfo = null,
            releasedate = "2018-03-15",
            name = "fake name"
        )
    }

    fun provideFakeArtist(): Artist {
        return Artist(
            id = "",
            image = AppConstants.DEFAULT_IMAGE,
            name = "fake artist",
            website = ""
        )
    }

    fun provideFakeAlbum(): Album {
        return Album(
            id = "",
            image = AppConstants.DEFAULT_IMAGE,
            name = "fake artist",
            artist_id = "",
            artist_name = "fake artist",
            releasedate = "",
            zip = ""
        )
    }
}