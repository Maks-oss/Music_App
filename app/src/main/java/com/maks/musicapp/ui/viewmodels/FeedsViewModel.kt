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

class FeedsViewModel(private val feedsRepository: FeedsRepository) : ViewModel() {
    var feedsUiState by mutableStateOf(UiState<Feed>())
        private set

    var feedsViewModelState by mutableStateOf(FeedsViewModelStates())
        private set

    fun setChipValue(value: String){
        feedsViewModelState = feedsViewModelState.copy(selectedChip = value)
    }
    fun applyExpandedCard(value: String){
        val expandedCard = feedsViewModelState.expandedFeedCards.toMutableList().apply {
            if (contains(value)){
                remove(value)
            } else {
                add(value)
            }
        }
        feedsViewModelState = feedsViewModelState.copy(expandedFeedCards = expandedCard)

    }

    fun setIsRefreshingValue(value: Boolean){
        feedsViewModelState = feedsViewModelState.copy(isRefreshing = value)
    }

    fun applyFeeds(type: String = "artist",isRefresh: Boolean = false) {
        feedsUiState = feedsUiState.copy(isLoading = true, message = null)
        viewModelScope.launch {
            feedsUiState = try {
                val feeds = if (isRefresh){
                    val serverFeeds = feedsRepository.getFeedsFromServer(type)
                    setIsRefreshingValue(false)
                    feedsRepository.insertFeeds(serverFeeds!!)
                    serverFeeds
                } else {
                    feedsRepository.getFeeds(type)
                }

                feedsUiState.copy(
                    isLoading = false,
                    message = null,
                    result = feeds
                )
            } catch (exc: Exception) {
                feedsUiState.copy(isLoading = false, message = exc.message, result = null)
            }
        }
    }
}