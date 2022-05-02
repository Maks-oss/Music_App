package com.maks.musicapp.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.*
import androidx.test.espresso.Espresso
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.common.truth.Truth.assertThat
import com.maks.musicapp.ui.BaseUiTest
import com.maks.musicapp.R
import com.maks.musicapp.fakedata.FakeDataProvider
import com.maks.musicapp.mappers.MusicMapper
import com.maks.musicapp.ui.navigation.TestNavHostController
import com.maks.musicapp.repository.MusicRepository
import com.maks.musicapp.ui.viewmodels.MusicViewModel
import com.maks.musicapp.ui.viewmodels.TrackViewModel
import com.maks.musicapp.utils.waitUntil
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Test

class TrackDetailScreenTest: BaseUiTest() {

    private val trackViewModel: TrackViewModel = TrackViewModel()

    @Test
    fun clickOnPlayButtonShouldStartPlayingMusic(){
        lateinit var playButton: String
        composeTestRule.setContent {
            playButton = stringResource(id = R.string.play_button)
            TrackDetailScreen(track = FakeDataProvider.provideFakeTrack(), trackViewModel = trackViewModel, snackbarHostState = mockk())
        }
        composeTestRule.onNodeWithContentDescription(playButton).performClick()
        composeTestRule.waitUntil(expression = {
            assertThat(trackViewModel.trackViewModelState.trackMinutes).isNonZero()
            composeTestRule.onNodeWithText("00:00").assertDoesNotExist()
        },2000)
    }

    @Test
    fun clickOnSliderShouldChangeCurrentMusicMinutes(){
        lateinit var playerSlider: String
        composeTestRule.setContent {
            playerSlider = stringResource(id = R.string.player_slider)
            TrackDetailScreen(track = FakeDataProvider.provideFakeTrack(), trackViewModel = trackViewModel, snackbarHostState = mockk())
        }
        composeTestRule.onNodeWithContentDescription(playerSlider).performClick()
        composeTestRule.waitUntil(expression = {
            assertThat(trackViewModel.trackViewModelState.trackMinutes).isNonZero()
            composeTestRule.onNodeWithText("00:00").assertDoesNotExist()
        },2000)

    }

    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    @OptIn(ExperimentalAnimationApi::class)
    @Test
    fun clickBackWhenMusicPlayingShouldStopMusic(){
        lateinit var playButton: String
        lateinit var textField: String
        val musicRepository = mockk<MusicRepository>()
        coEvery { musicRepository.getTracksByName(any()) } returns FakeDataProvider.provideFakeTrackResultList()
        composeTestRule.setContent {
            playButton = stringResource(id = R.string.play_button)
            textField = stringResource(id = R.string.music_text_field_description)
            TestNavHostController(musicViewModel = MusicViewModel(musicRepository, MusicMapper()), navController = rememberAnimatedNavController())
        }
        composeTestRule.onNodeWithContentDescription(textField).performTextInput("sorry")
        composeTestRule.waitUntil(expression = {
            composeTestRule.onNodeWithText("fake artist - fake track").performClick()
            composeTestRule.onNodeWithContentDescription(playButton).performClick()
        },2000)

        composeTestRule.waitUntil(expression = {
            Espresso.pressBack()
            composeTestRule.onNodeWithText("fake artist - fake track").performClick()
            composeTestRule.onNodeWithText("00:00").assertIsDisplayed()
            composeTestRule.onNodeWithContentDescription("Pause Button").assertDoesNotExist()
        },5000)

    }
}