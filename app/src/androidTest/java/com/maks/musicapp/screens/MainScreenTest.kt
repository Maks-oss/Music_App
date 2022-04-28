package com.maks.musicapp.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
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
import com.maks.musicapp.utils.AppConstants
import com.maks.musicapp.utils.AsyncTimer
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
class MainScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var musicViewModel: MusicViewModel

    private lateinit var musicRepository: MusicRepository

    @Before
    fun setup() {
        musicRepository = mockk()
        coEvery { musicRepository.getTracksByName(any()) } returns FakeDataProvider.provideFakeTrackResult()
        coEvery { musicRepository.getAlbumsByName(any()) } returns FakeDataProvider.provideFakeAlbumResult()
        coEvery { musicRepository.getArtistsByName(any()) } returns FakeDataProvider.provideFakeArtistResult()
        musicViewModel = MusicViewModel(musicRepository, MusicMapper())
    }

    @Test
    fun testSearchingForTracksShouldDisplayShimmerAnimation() {
        lateinit var textField: String
        lateinit var shimmerItem: String
        composeTestRule.setContent {
            textField = stringResource(id = R.string.music_text_field_description)
            shimmerItem = stringResource(id = R.string.shimmer_animation_item)
            MusicAppTheme {
                MainScreen(
                    musicViewModel = musicViewModel,
                    navController = mockk(),
                    snackbarHostState = mockk()
                )
            }
        }
        composeTestRule.onNodeWithContentDescription(textField).performTextInput("sorry")
        AsyncTimer.start()
        composeTestRule.waitUntil(2000) {
            AsyncTimer.expired
        }
        composeTestRule.onAllNodesWithContentDescription(shimmerItem).onFirst().assertIsDisplayed()

    }

    @Test
    fun testSearchingForTracksShouldDisplayTracks() {

        lateinit var textField: String
        composeTestRule.setContent {
            textField = stringResource(id = R.string.music_text_field_description)
            MusicAppTheme {
                MainScreen(
                    musicViewModel = musicViewModel,
                    navController = mockk(),
                    snackbarHostState = mockk()
                )
            }
        }
        composeTestRule.onNodeWithContentDescription(textField).performTextInput("sorry")
        AsyncTimer.start(2000)
        composeTestRule.waitUntil(3000) {
            AsyncTimer.expired
        }
        composeTestRule.onNodeWithText("fake artist - fake track").assertIsDisplayed()
    }

    @Test
    fun testSearchingForTracksShouldDisplayEmptyResponseMessage() {
        coEvery { musicRepository.getTracksByName(any()) } returns null
        lateinit var textField: String
        composeTestRule.setContent {
            textField = stringResource(id = R.string.music_text_field_description)
            val scaffoldState = rememberScaffoldState()
            MusicAppTheme {
                Scaffold(scaffoldState = scaffoldState) {
                    MainScreen(
                        musicViewModel = musicViewModel,
                        navController = mockk(),
                        snackbarHostState = scaffoldState.snackbarHostState
                    )
                }
            }
        }
        composeTestRule.onNodeWithContentDescription(textField).performTextInput("sorry")
        AsyncTimer.start(2000)
        composeTestRule.waitUntil(3000) {
            AsyncTimer.expired
        }
        composeTestRule.onNodeWithText(AppConstants.EMPTY_RESULT_MESSAGE).assertIsDisplayed()
    }

    @Test
    fun testSwitchingTabsShouldDisplayDifferentListItems() {
        lateinit var textField: String
        lateinit var albums: String
        lateinit var artists: String
        lateinit var tracks: String
        composeTestRule.setContent {
            textField = stringResource(id = R.string.music_text_field_description)
            albums = stringResource(id = R.string.albums)
            artists = stringResource(id = R.string.artists)
            tracks = stringResource(id = R.string.tracks)
            MusicAppTheme {
                MainScreen(
                    musicViewModel = musicViewModel,
                    navController = mockk(),
                    snackbarHostState = mockk()
                )

            }
        }
        composeTestRule.onNodeWithContentDescription(textField).performTextInput("sorry")
        AsyncTimer.start(2000)
        composeTestRule.waitUntil(3000) {
            AsyncTimer.expired
        }

        composeTestRule.onNodeWithText(artists).performClick()
        composeTestRule.onNodeWithText("fake artist").assertIsDisplayed()

        composeTestRule.onNodeWithText(albums).performClick()
        composeTestRule.onNodeWithText("fake album").assertIsDisplayed()

        composeTestRule.onNodeWithText(tracks).performClick()
        composeTestRule.onNodeWithText("fake artist - fake track").assertIsDisplayed()

    }

    @Test
    fun testSwitchingTabsShouldDisplayEmptyResponse() {
        coEvery { musicRepository.getArtistsByName(any()) } returns null
        lateinit var textField: String
        lateinit var artists: String
        composeTestRule.setContent {
            textField = stringResource(id = R.string.music_text_field_description)
            artists = stringResource(id = R.string.artists)
            val scaffoldState = rememberScaffoldState()
            MusicAppTheme {
                Scaffold(scaffoldState = scaffoldState) {
                    MainScreen(
                        musicViewModel = musicViewModel,
                        navController = mockk(),
                        snackbarHostState = scaffoldState.snackbarHostState
                    )
                }

            }
        }
        composeTestRule.onNodeWithContentDescription(textField).performTextInput("sorry")
        AsyncTimer.start(2000)
        composeTestRule.waitUntil(3000) {
            AsyncTimer.expired
        }

        composeTestRule.onNodeWithText(artists).performClick()
        composeTestRule.onNodeWithText(AppConstants.EMPTY_RESULT_MESSAGE).assertIsDisplayed()


    }
}