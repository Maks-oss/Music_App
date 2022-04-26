package com.maks.musicapp.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.maks.musicapp.R
import com.maks.musicapp.fakedata.FakeDataProvider
import com.maks.musicapp.mappers.MusicMapper
import com.maks.musicapp.repository.MusicRepository
import com.maks.musicapp.ui.screens.MainScreen
import com.maks.musicapp.ui.theme.MusicAppTheme
import com.maks.musicapp.ui.viewmodels.MusicViewModel
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var musicViewModel: MusicViewModel
    @Before
    fun setup(){
        val musicRepository = mockk<MusicRepository>()
        coEvery { musicRepository.getTracksByName(any()) } returns FakeDataProvider.provideFakeTrackResult()
        coEvery { musicRepository.getAlbumsByName(any()) } returns FakeDataProvider.provideFakeAlbumResult()
        coEvery { musicRepository.getArtistsByName(any()) } returns FakeDataProvider.provideFakeArtistResult()
        musicViewModel = MusicViewModel(musicRepository, MusicMapper())
    }
    @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
    @Test
    fun searchingForTracksShouldDisplayShimmerAnimation() {
        lateinit var textField: String
        lateinit var shimmerItem: String
        composeTestRule.setContent {
            textField = stringResource(id = R.string.music_text_field_description)
            shimmerItem = stringResource(id = R.string.shimmer_animation_item)
            MusicAppTheme {
                MainScreen(musicViewModel = musicViewModel, navController = mockk(), snackbarHostState = mockk())
            }
        }
        composeTestRule.onNodeWithContentDescription(textField).performTextInput("sorry")
        Thread.sleep(1000)
        composeTestRule.onNodeWithContentDescription(shimmerItem).assertIsDisplayed()
    }
}