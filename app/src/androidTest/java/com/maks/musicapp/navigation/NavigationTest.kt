package com.maks.musicapp.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.maks.musicapp.MainActivity
import com.maks.musicapp.R
import com.maks.musicapp.data.domain.Artist
import com.maks.musicapp.fakedata.FakeDataProvider
import com.maks.musicapp.mappers.MusicMapper
import com.maks.musicapp.repository.MusicRepository
import com.maks.musicapp.ui.theme.MusicAppTheme
import com.maks.musicapp.ui.viewmodels.MusicViewModel
import com.maks.musicapp.utils.AsyncTimer
import com.maks.musicapp.utils.Routes
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
class NavigationTest {
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
    fun clickOnTrackListItemShouldNavigateToTrackDetail() {
        lateinit var textField: String
        lateinit var navController: NavHostController
        composeTestRule.setContent {
            navController = rememberAnimatedNavController()
            textField = stringResource(id = R.string.music_text_field_description)
            MusicAppTheme {
                TestNavHostController(musicViewModel = musicViewModel,navController)
            }
        }
        composeTestRule.onNodeWithContentDescription(textField).performTextInput("sorry")
        AsyncTimer.start(2000)
        composeTestRule.waitUntil(3000) {
            AsyncTimer.expired
        }
        composeTestRule.onNodeWithText("fake artist - fake track").performClick()

        assert(navController.currentBackStackEntry?.destination?.route == Routes.TrackDetailsScreenRoute.route)
    }

    @Test
    fun clickOnArtistListItemShouldNavigateToArtistDetail() {
        lateinit var textField: String
        lateinit var navController: NavHostController
        lateinit var artists: String
        composeTestRule.setContent {
            navController = rememberAnimatedNavController()
            artists = stringResource(id = R.string.artists)
            textField = stringResource(id = R.string.music_text_field_description)
            MusicAppTheme {
                TestNavHostController(musicViewModel = musicViewModel,navController)
            }
        }
        composeTestRule.onNodeWithContentDescription(textField).performTextInput("sorry")
        AsyncTimer.start(2000)
        composeTestRule.waitUntil(3000) {
            AsyncTimer.expired
        }
        composeTestRule.onNodeWithText(artists).performClick()
        composeTestRule.onNodeWithText("fake artist").performClick()

        assert(navController.currentBackStackEntry?.destination?.route == Routes.ArtistDetailsScreenRoute.route)
    }

    @Test
    fun clickOnAlbumListItemShouldNavigateToAlbumDetail() {
        lateinit var textField: String
        lateinit var navController: NavHostController
        lateinit var albums: String
        composeTestRule.setContent {
            navController = rememberAnimatedNavController()
            textField = stringResource(id = R.string.music_text_field_description)
            albums = stringResource(id = R.string.albums)
            MusicAppTheme {
                TestNavHostController(musicViewModel = musicViewModel,navController)
            }
        }
        composeTestRule.onNodeWithContentDescription(textField).performTextInput("sorry")
        AsyncTimer.start(2000)
        composeTestRule.waitUntil(3000) {
            AsyncTimer.expired
        }
        composeTestRule.onNodeWithText(albums).performClick()
        composeTestRule.onNodeWithText("fake album").performClick()

        assert(navController.currentBackStackEntry?.destination?.route == Routes.AlbumDetailsScreenRoute.route)
    }

}