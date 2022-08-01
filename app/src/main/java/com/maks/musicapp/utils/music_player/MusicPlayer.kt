package com.maks.musicapp.utils.music_player

import java.io.Serializable

interface MusicPlayer : Serializable {
    fun play()
    fun pause()
    fun duration(): Float
    fun seekTo(value: Float)
}