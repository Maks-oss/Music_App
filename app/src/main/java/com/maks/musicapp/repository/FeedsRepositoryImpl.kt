package com.maks.musicapp.repository

import com.maks.musicapp.BuildConfig
import com.maks.musicapp.data.dto.feeds.FeedResult
import com.maks.musicapp.retrofit.retrofitservices.MusicService

class FeedsRepositoryImpl(private val musicService: MusicService) : FeedsRepository {
    override suspend fun getFeeds(type: String): List<FeedResult>? =
        musicService.getFeedsResponse(BuildConfig.clientId, type).body()?.results
}