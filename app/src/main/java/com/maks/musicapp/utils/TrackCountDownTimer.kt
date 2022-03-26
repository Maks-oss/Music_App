package com.maks.musicapp.utils

import android.media.MediaPlayer
import android.os.CountDownTimer
import androidx.compose.runtime.MutableState

class TrackCountDownTimer(
    millis: Long, countDown: Long = 100,
    private val minutes: MutableState<Float>,
    private val mediaPlayer: MediaPlayer,
    private val isPlaying: MutableState<Boolean>
) : CountDownTimer(millis, countDown) {

    override fun onTick(millisUntilFinished: Long) {
        minutes.value = mediaPlayer.currentPosition.toFloat()
        if (mediaPlayer.currentPosition == mediaPlayer.duration) {
            isPlaying.value = false
        }
    }

    override fun onFinish() {
    }
}