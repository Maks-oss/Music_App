package com.maks.musicapp.repository

import com.maks.musicapp.BuildConfig
import com.maks.musicapp.data.music.TrackResult
import com.maks.musicapp.retrofit.retrofitservices.TrackService

class TrackRepositoryImpl(private val trackService: TrackService) : TrackRepository {
    override suspend fun getTracksByName(name: String): List<TrackResult>? =
        trackService.getTrackResponse(BuildConfig.clientId, name).body()?.trackResults
}