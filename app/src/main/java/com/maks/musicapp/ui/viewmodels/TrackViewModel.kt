package com.maks.musicapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.maks.musicapp.utils.player.MusicPlayer
import kotlin.properties.Delegates

class TrackViewModel : ViewModel() {
    var trackViewModelState by mutableStateOf(TrackViewModelState())
        private set

    var musicPlayer: MusicPlayer by Delegates.notNull()

    fun setIsTrackPlayingValue(value: Boolean) {
        trackViewModelState = trackViewModelState.copy(isTrackPlaying = value)
    }

    fun setTrackMinutesValue(value: Float) {
        trackViewModelState = trackViewModelState.copy(trackMinutes = value)
    }

    fun playTrack() {
        if (!trackViewModelState.isTrackPlaying) {
            musicPlayer.play()
        } else {
            musicPlayer.pause()
        }
        setIsTrackPlayingValue(!trackViewModelState.isTrackPlaying)
    }

    fun seekTo(value: Float) {
        setTrackMinutesValue(value)
        musicPlayer.seekTo(value)
    }

    fun stopTrack() {
        setTrackMinutesValue(0f)
        musicPlayer.pause()
    }

    fun trackDuration() = musicPlayer.duration()

    fun setOnBackPressed(value: Boolean) {
        trackViewModelState = trackViewModelState.copy(onBackPressed = value)
    }
}