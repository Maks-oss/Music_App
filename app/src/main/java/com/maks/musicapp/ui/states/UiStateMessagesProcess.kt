package com.maks.musicapp.ui.states

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import com.maks.musicapp.utils.showMessage

@Composable
fun <T> ProcessUiStateMessages(
    uiState: UiState<T>,
    snackbarHostState: SnackbarHostState,
    messageShown: () -> Unit
) {
    val errorMessage = uiState.message
    if (!errorMessage.isNullOrEmpty()) {
        snackbarHostState.showMessage(errorMessage)
        messageShown()
    }
}


