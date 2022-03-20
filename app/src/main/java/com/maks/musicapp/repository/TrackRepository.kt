package com.maks.musicapp.repository

import com.maks.musicapp.data.music.TrackResult

interface TrackRepository {
    suspend fun getTracksByName(name: String): List<TrackResult>?
}