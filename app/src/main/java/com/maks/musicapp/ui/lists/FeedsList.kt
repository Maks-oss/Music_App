package com.maks.musicapp.ui.lists

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.maks.musicapp.ui.animation.DisplayShimmer
import com.maks.musicapp.ui.composeutils.ExpandableFeedCard
import com.maks.musicapp.ui.viewmodels.FeedsViewModel

@ExperimentalFoundationApi
@Composable
fun FeedsList(
    feedsViewModel: FeedsViewModel,
) {
    val feedsUiState = feedsViewModel.feedsUiState
    val expandedCards by feedsViewModel.expandedFeedCards
    DisplayShimmer(feedsUiState.isLoading,isVertical = false)
    val feeds = feedsUiState.result
    feeds?.let { feedList ->
        LazyColumn {
            items(feedList) {
                ExpandableFeedCard(feed = it, onCardArrowClick = { feedsViewModel.applyExpandedCard(it.id) }, expanded = expandedCards.contains(it.id))
            }
        }
    }
}


