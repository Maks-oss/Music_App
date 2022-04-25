package com.maks.musicapp.repository

import android.util.Log
import com.maks.musicapp.BuildConfig
import com.maks.musicapp.data.domain.Feed
import com.maks.musicapp.data.dto.feeds.FeedResult
import com.maks.musicapp.mappers.FeedsMapper
import com.maks.musicapp.retrofit.retrofitservices.MusicService
import com.maks.musicapp.room.FeedDao

class FeedsRepositoryImpl(
    private val musicService: MusicService,
    private val feedsDao: FeedDao,
    private val feedsMapper: FeedsMapper
) :
    FeedsRepository {
    override suspend fun getFeeds(type: String): List<Feed>? {
        val feeds = feedsDao.getFeeds(type)
        return if (feeds.isNullOrEmpty()) {
            val feedsFromServer = feedsMapper.toFeedList(
                musicService.getFeedsResponse(BuildConfig.clientId, type).body()?.results
            )
            feedsDao.insertFeeds(feedsFromServer!!)
            feedsFromServer
        } else {
            feeds
        }
    }
}