package com.maks.musicapp.utils

import androidx.compose.ui.test.junit4.ComposeContentTestRule

fun ComposeContentTestRule.waitUntil(expression:()->Unit, delay: Long = 1000){
    AsyncTimer.start(delay = delay)
    waitUntil(delay + 1000) {
        AsyncTimer.expired
    }
    expression()
}