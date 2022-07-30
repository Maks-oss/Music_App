package com.maks.musicapp.ui.viewmodels

data class TrackViewModelState (
    val isTrackPlaying:Boolean = false,
    val trackMinutes:Float = 0F,
    val isAudioPlayerLoading: Boolean = false
)