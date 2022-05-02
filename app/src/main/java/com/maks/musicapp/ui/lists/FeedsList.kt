package com.maks.musicapp.ui.lists

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.maks.musicapp.R
import com.maks.musicapp.ui.animation.DisplayShimmer
import com.maks.musicapp.ui.composeutils.ExpandableFeedCard
import com.maks.musicapp.ui.viewmodels.FeedsViewModel

@ExperimentalFoundationApi
@Composable
fun FeedsList(
    feedsViewModel: FeedsViewModel,
) {
    val feedsUiState = feedsViewModel.feedsUiState
    val expandedCards = feedsViewModel.feedsViewModelState.expandedFeedCards
    DisplayShimmer(feedsUiState.isLoading,isVertical = false)
    val feedsColumn = stringResource(id = R.string.feeds_column)
    val feeds = feedsUiState.result
    feeds?.let { feedList ->
        LazyColumn(modifier = Modifier.semantics { contentDescription = feedsColumn }) {
            items(feedList) {
                ExpandableFeedCard(feed = it, onCardArrowClick = { feedsViewModel.applyExpandedCard(it.id) }, expanded = expandedCards.contains(it.id))
            }
        }
    }
}


