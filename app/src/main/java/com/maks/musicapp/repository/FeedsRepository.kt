package com.maks.musicapp.repository

import com.maks.musicapp.data.dto.albums.tracks.AlbumTracksResult
import com.maks.musicapp.data.dto.feeds.FeedResult

interface FeedsRepository {
    suspend fun getFeeds(type: String): List<FeedResult>?
}