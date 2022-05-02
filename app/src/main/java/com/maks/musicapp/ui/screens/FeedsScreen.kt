package com.maks.musicapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.maks.musicapp.R
import com.maks.musicapp.ui.composeutils.CustomChip
import com.maks.musicapp.ui.lists.FeedsList
import com.maks.musicapp.ui.viewmodels.FeedsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedsScreen(feedsViewModel: FeedsViewModel) {
    val feedsCategoriesList = stringArrayResource(id = R.array.feeds_categories)
    val selected = feedsViewModel.feedsViewModelState.selectedChip
    val isRefreshing = feedsViewModel.feedsViewModelState.isRefreshing
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            feedsViewModel.setIsRefreshingValue(true)
            feedsViewModel.applyFeeds(selected, true)
        }) {
        Column {
            Row(
                Modifier
                    .padding(8.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                feedsCategoriesList.forEach { category ->
                    CustomChip(selected == category, category, action = {
                        feedsViewModel.setChipValue(category)
                        feedsViewModel.applyFeeds(category)
                    })
                }
            }
            FeedsList(feedsViewModel)
        }
    }

}