package com.maks.musicapp.data.dto.feeds

import com.maks.musicapp.data.dto.Headers

data class Feeds(
    val headers: Headers,
    val results: List<FeedResult>
)