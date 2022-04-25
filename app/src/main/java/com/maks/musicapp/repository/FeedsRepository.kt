package com.maks.musicapp.repository

import com.maks.musicapp.data.domain.Feed
import com.maks.musicapp.data.dto.albums.tracks.AlbumTracksResult
import com.maks.musicapp.data.dto.feeds.FeedResult

interface FeedsRepository {
    suspend fun getFeeds(type: String): List<Feed>?
    suspend fun getFeedsFromServer(type: String): List<Feed>?
    suspend fun insertFeeds(feeds: List<Feed>)
}