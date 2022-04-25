package com.maks.musicapp.ui.viewmodels

import androidx.compose.runtime.mutableStateOf

data class FeedsViewModelStates (
    val selectedChip: String = "artist",
    val expandedFeedCards: List<String> = emptyList(),
    val isRefreshing: Boolean = false
)