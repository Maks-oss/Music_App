package com.maks.musicapp.utils.player

interface MusicPlayer {
    fun play()
    fun pause()
    fun duration() : Float
    fun seekTo(value: Float)
}