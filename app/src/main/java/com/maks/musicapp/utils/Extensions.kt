package com.maks.musicapp.utils

import com.maks.musicapp.data.dto.artists.tracks.ArtistTracksResult
import com.maks.musicapp.data.dto.tracks.TrackResult
import java.util.concurrent.TimeUnit

fun Number.toMinutes(): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this.toLong())
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this.toLong()) -
            TimeUnit.MINUTES.toSeconds(minutes)
    return "${if (minutes < 10) "0$minutes" else minutes}:${if (seconds < 10) "0$seconds" else seconds}"

}

fun ArtistTracksResult.toTrackResult() = TrackResult(
    album_id = this.album_id,
    album_image = this.album_image,
    album_name = this.album_name,
    artist_id = "",
    artist_idstr = "",
    artist_name = this.album_name,
    audio = this.audio,
    audiodownload = this.audiodownload,
    audiodownload_allowed = this.audiodownload_allowed,
    duration = this.duration.toInt(),
    id = this.id,
    image = this.image,
    license_ccurl = this.license_ccurl,
    musicinfo = null,
    name = this.name,
    position = 0,
    prourl = "",
    releasedate = this.releasedate,
    shareurl = "",
    shorturl = "",
    waveform = ""
)