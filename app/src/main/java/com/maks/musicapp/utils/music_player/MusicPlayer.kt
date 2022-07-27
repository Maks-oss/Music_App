package com.maks.musicapp.utils.music_player

interface MusicPlayer {
    fun play()
    fun pause()
    fun duration() : Float
    fun seekTo(value: Float)
}