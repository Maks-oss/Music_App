package com.maks.musicapp.ui.lists

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maks.musicapp.data.domain.Feed
import com.maks.musicapp.ui.animation.DisplayShimmer
import com.maks.musicapp.ui.states.UiState

@ExperimentalFoundationApi
@Composable
fun FeedsList(
    feedsUiState: UiState<Feed>,
) {
    DisplayShimmer(feedsUiState.isLoading,isVertical = false)
    val feeds = feedsUiState.result
    feeds?.let { feedList ->
        LazyColumn {
            items(feedList) {
                FeedsListItem(feed = it)
            }
        }
    }
}


@Composable
fun FeedsListItem(feed: Feed) {
    Card(
        modifier = Modifier
            .padding(8.dp), elevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = feed.title, style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = feed.text, style = MaterialTheme.typography.body1)
        }
    }

}

