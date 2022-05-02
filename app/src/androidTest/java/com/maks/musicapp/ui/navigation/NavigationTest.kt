package com.maks.musicapp.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.maks.musicapp.ui.BaseUiTest
import com.maks.musicapp.R
import com.maks.musicapp.fakedata.FakeDataProvider
import com.maks.musicapp.mappers.MusicMapper
import com.maks.musicapp.repository.MusicRepository
import com.maks.musicapp.ui.theme.MusicAppTheme
import com.maks.musicapp.ui.viewmodels.MusicViewModel
import com.maks.musicapp.utils.Routes
import com.maks.musicapp.utils.waitUntil
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
class NavigationTest: BaseUiTest() {


    private lateinit var musicViewModel: MusicViewModel

    private lateinit var musicRepository: MusicRepository

    @Before
    fun setup() {
        musicRepository = mockk()
        coEvery { musicRepository.getTracksByName(any()) } returns FakeDataProvider.provideFakeTrackResultList()
        coEvery { musicRepository.getAlbumsByName(any()) } returns FakeDataProvider.provideFakeAlbumResultList()
        coEvery { musicRepository.getArtistsByName(any()) } returns FakeDataProvider.provideFakeArtistResultList()
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
        composeTestRule.waitUntil(expression = {
            composeTestRule.onNodeWithText("fake artist - fake track").performClick()
            assert(navController.currentBackStackEntry?.destination?.route == Routes.TrackDetailsScreenRoute.route)
        },delay = 2000)
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

        composeTestRule.waitUntil(expression = {
            composeTestRule.onNodeWithText(artists).performClick()
            composeTestRule.onNodeWithText("fake artist").performClick()

            assert(navController.currentBackStackEntry?.destination?.route == Routes.ArtistDetailsScreenRoute.route)
        },delay = 2000)
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
        composeTestRule.waitUntil(expression = {
            composeTestRule.onNodeWithText(albums).performClick()
            composeTestRule.onNodeWithText("fake album").performClick()

            assert(navController.currentBackStackEntry?.destination?.route == Routes.AlbumDetailsScreenRoute.route)

        },delay = 2000)

    }

}