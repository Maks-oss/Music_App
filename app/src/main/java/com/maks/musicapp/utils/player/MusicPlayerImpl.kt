package com.maks.musicapp.utils.player

import android.media.MediaPlayer
import android.os.CountDownTimer
import java.io.Serializable

class MusicPlayerImpl(
    private val mediaPlayer: MediaPlayer,
    private val onTick: () -> Unit
) : MusicPlayer, Serializable {

    private val trackCountDownTimer = object : CountDownTimer(mediaPlayer.duration.toLong(), 100) {
        override fun onTick(millisUntilFinished: Long) {
            onTick()
        }
        override fun onFinish() {}
    }

    override fun play() {
        mediaPlayer.start()
        trackCountDownTimer.start()
    }

    override fun pause() {
        mediaPlayer.pause()
        trackCountDownTimer.cancel()
    }

    override fun duration(): Float = mediaPlayer.duration.toFloat()

    override fun seekTo(value: Float) {
        mediaPlayer.seekTo(value.toInt())
    }

}