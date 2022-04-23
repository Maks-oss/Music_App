package com.maks.musicapp.data.dto.feeds

data class FeedResult(
    val date_end: String,
    val date_start: String,
    val id: String,
    val images: Images,
    val joinid: String,
    val lang: List<String>,
    val link: String,
    val position: String,
    val subtitle: List<Any>,
    val target: String,
    val text: Text,
    val title: Title,
    val type: String
)