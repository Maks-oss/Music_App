package com.maks.musicapp.mappers

import com.maks.musicapp.data.domain.Feed
import com.maks.musicapp.data.dto.feeds.FeedResult

class FeedsMapper {
    fun toFeedList(feedResultList: List<FeedResult>?): List<Feed>? {
        return feedResultList?.map { feedResult ->
            Feed(title = feedResult.title.en, text = feedResult.text.en, type = feedResult.type, id = feedResult.id)
        }?.filter { it.title.isNotEmpty() }
    }
}