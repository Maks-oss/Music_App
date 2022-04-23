package com.maks.musicapp.ui.states

import com.maks.musicapp.data.domain.Artist

data class UiState<T>(
    val isLoading: Boolean = false,
    val message: String? = null,
    val result: List<T>? = null
)