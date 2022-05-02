package com.maks.musicapp.ui

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import com.maks.musicapp.utils.AsyncTimer
import org.junit.Rule

open class BaseUiTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    fun ComposeContentTestRule.waitUntil(expression:()->Unit, delay: Long = 1000){
        AsyncTimer.start(delay = delay)
        waitUntil(delay + 1000) {
            AsyncTimer.expired
        }
        expression()
    }
}