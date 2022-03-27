package com.maks.musicapp.utils

import android.media.MediaPlayer
import android.os.CountDownTimer
import com.maks.musicapp.viewmodels.MusicViewModel

class TrackCountDownTimer(
    millis: Long, countDown: Long = 100,
    private val musicViewModelStates: MusicViewModel.MusicViewModelStates,
    private val mediaPlayer: MediaPlayer,
) : CountDownTimer(millis, countDown) {

    override fun onTick(millisUntilFinished: Long) {
        musicViewModelStates.setTrackMinutesValue(mediaPlayer.currentPosition.toFloat())
        if (mediaPlayer.currentPosition == mediaPlayer.duration) {
            musicViewModelStates.setIsTrackPlayingValue(false)
        }
    }

    override fun onFinish() {

    }
}