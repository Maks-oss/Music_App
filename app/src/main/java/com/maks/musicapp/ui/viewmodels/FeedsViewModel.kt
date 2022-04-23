package com.maks.musicapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maks.musicapp.data.domain.Feed
import com.maks.musicapp.mappers.FeedsMapper
import com.maks.musicapp.repository.FeedsRepository
import com.maks.musicapp.ui.states.UiState
import kotlinx.coroutines.launch

class FeedsViewModel(
    private val feedsRepository: FeedsRepository,
    private val feedsMapper: FeedsMapper
) : ViewModel() {
    var feedsUiState by mutableStateOf(UiState<Feed>())
        private set

    fun applyFeeds(type: String = "artist") {
        feedsUiState = feedsUiState.copy(isLoading = true, message = null)
        viewModelScope.launch {
            feedsUiState = try {
                val feeds = feedsRepository.getFeeds(type)
                feedsUiState.copy(
                    isLoading = false,
                    message = null,
                    result = feedsMapper.toFeedList(feeds ?: emptyList())
                )
            } catch (exc: Exception) {
                feedsUiState.copy(isLoading = false, message = exc.message, result = null)
            }
        }
    }
}