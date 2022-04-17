package com.maks.musicapp.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.Job

data class MusicViewModelStates(
    val searchInput:String = "",
    val currentJob:Job? = null,
    val tabState:Int = 0,
    val textFieldVisibility:Boolean = true
)