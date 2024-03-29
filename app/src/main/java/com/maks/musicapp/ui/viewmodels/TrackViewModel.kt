package com.maks.musicapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.maks.musicapp.utils.music_player.MusicPlayer
import kotlin.properties.Delegates

class TrackViewModel : ViewModel() {
    var trackViewModelState by mutableStateOf(TrackViewModelState())
        private set

//    var musicPlayer: MusicPlayer by Delegates.notNull()
    var musicPlayer: MusicPlayer? by mutableStateOf(null)
        private set
    fun setIsTrackPlayingValue(value: Boolean) {
        trackViewModelState = trackViewModelState.copy(isTrackPlaying = value)
    }
    fun setMusicPlayerValue(musicPlayer: MusicPlayer?){
        this.musicPlayer = musicPlayer
    }
    fun setIsAudioPlayerLoadingValue(value: Boolean){
        trackViewModelState = trackViewModelState.copy(isAudioPlayerLoading = value)
    }

    fun setTrackMinutesValue(value: Float) {
        trackViewModelState = trackViewModelState.copy(trackMinutes = value)
    }

    fun playTrack() {
        if (!trackViewModelState.isTrackPlaying) {
            musicPlayer?.play()
        } else {
            musicPlayer?.pause()
        }
        setIsTrackPlayingValue(!trackViewModelState.isTrackPlaying)
    }

    fun seekTo(value: Float) {
        setTrackMinutesValue(value)
        musicPlayer?.seekTo(value)
    }

    fun stopTrack() {
        setTrackMinutesValue(0f)
        setIsTrackPlayingValue(false)
        setMusicPlayerValue(null)
        musicPlayer?.pause()
    }
    fun resetMusicPlayer(){
        setTrackMinutesValue(0f)
        setIsTrackPlayingValue(false)
        setMusicPlayerValue(null)
    }

}