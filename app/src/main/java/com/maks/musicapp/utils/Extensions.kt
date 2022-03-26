package com.maks.musicapp.utils

import java.util.concurrent.TimeUnit

fun Number.toMinutes(): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this.toLong())
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this.toLong()) -
            TimeUnit.MINUTES.toSeconds(minutes)
    return "${if (minutes < 10) "0$minutes" else minutes}:${if (seconds < 10) "0$seconds" else seconds}"

}