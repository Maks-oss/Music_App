package com.maks.musicapp.ui

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule

open class BaseUiTest {
    @get:Rule
    val composeTestRule = createComposeRule()
}