package com.maks.musicapp.fakedata

import com.maks.musicapp.data.domain.Track
import com.maks.musicapp.data.dto.albums.AlbumResult
import com.maks.musicapp.data.dto.artists.ArtistResult
import com.maks.musicapp.data.dto.tracks.TrackResult
import com.maks.musicapp.utils.AppConstants

object FakeDataProvider {
    fun provideFakeTrackResultList(): List<TrackResult> {
        return listOf(
            TrackResult("", AppConstants.DEFAULT_IMAGE, "fake name", "", "", "fake artist", "https://prod-1.storage.jamendo.com//?trackid=1532771&format=mp31&from=app-devsite","",false,0,"1","","",null,"fake track",0,"","","","",""),
            TrackResult("", AppConstants.DEFAULT_IMAGE, "fake name2", "", "", "fake artist2", "","",false,0,"2","","",null,"fake track2",0,"","","","",""),
            TrackResult("", AppConstants.DEFAULT_IMAGE, "fake name3", "", "", "fake artist3", "","",false,0,"3","","",null,"fake track3",0,"","","","",""),
        )
    }
    fun provideFakeAlbumResultList(): List<AlbumResult> {
        return listOf(
            AlbumResult("1","fake name","1",AppConstants.DEFAULT_IMAGE,"fake album","","","","",false),
            AlbumResult("2","fake name2","2",AppConstants.DEFAULT_IMAGE,"fake album2","","","","",false),
            AlbumResult("3","fake name3","3",AppConstants.DEFAULT_IMAGE,"fake album3","","","","",false),
        )
    }
    fun provideFakeArtistResultList(): List<ArtistResult> {
        return listOf(
            ArtistResult("1",AppConstants.DEFAULT_IMAGE,"","fake artist","","",""),
            ArtistResult("2",AppConstants.DEFAULT_IMAGE,"","fake artist2","","",""),
            ArtistResult("3",AppConstants.DEFAULT_IMAGE,"","fake artist3","","",""),
        )
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
}