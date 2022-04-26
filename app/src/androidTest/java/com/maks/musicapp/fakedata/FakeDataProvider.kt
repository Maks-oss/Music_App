package com.maks.musicapp.fakedata

import com.maks.musicapp.data.dto.albums.AlbumResult
import com.maks.musicapp.data.dto.artists.ArtistResult
import com.maks.musicapp.data.dto.tracks.TrackResult
import com.maks.musicapp.utils.AppConstants

object FakeDataProvider {
    fun provideFakeTrackResult(): List<TrackResult> {
        return listOf(
            TrackResult("", AppConstants.DEFAULT_IMAGE, "fake name", "", "", "fake artist", "","",false,0,"1","","",null,"fake name",0,"","","","",""),
            TrackResult("", AppConstants.DEFAULT_IMAGE, "fake name2", "", "", "fake artist2", "","",false,0,"2","","",null,"fake name2",0,"","","","",""),
            TrackResult("", AppConstants.DEFAULT_IMAGE, "fake name3", "", "", "fake artist3", "","",false,0,"3","","",null,"fake name3",0,"","","","",""),
        )
    }
    fun provideFakeAlbumResult(): List<AlbumResult> {
        return listOf(
            AlbumResult("1","fake name","1",AppConstants.DEFAULT_IMAGE,"fake name","","","","",false),
            AlbumResult("2","fake name2","2",AppConstants.DEFAULT_IMAGE,"fake name2","","","","",false),
            AlbumResult("3","fake name3","3",AppConstants.DEFAULT_IMAGE,"fake name3","","","","",false),
        )
    }
    fun provideFakeArtistResult(): List<ArtistResult> {
        return listOf(
            ArtistResult("1",AppConstants.DEFAULT_IMAGE,"","fake name","","",""),
            ArtistResult("2",AppConstants.DEFAULT_IMAGE,"","fake name2","","",""),
            ArtistResult("3",AppConstants.DEFAULT_IMAGE,"","fake name3","","",""),
        )
    }
}