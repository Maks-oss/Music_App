package com.maks.musicapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import com.maks.musicapp.ui.lists.FeedsList
import com.maks.musicapp.ui.viewmodels.FeedsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedsScreen(feedsViewModel: FeedsViewModel){
    FeedsList(feedsUiState = feedsViewModel.feedsUiState)
}