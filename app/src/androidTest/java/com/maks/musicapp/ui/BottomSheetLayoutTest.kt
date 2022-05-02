package com.maks.musicapp.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.ui.test.*
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.maks.musicapp.fakedata.FakeDataProvider
import com.maks.musicapp.mappers.MusicMapper
import com.maks.musicapp.repository.MusicRepository
import com.maks.musicapp.ui.screens.AlbumDetailScreen
import com.maks.musicapp.ui.screens.ArtistDetailScreen
import com.maks.musicapp.ui.viewmodels.MusicViewModel
import com.maks.musicapp.utils.AppConstants
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class BottomSheetLayoutTest : BaseUiTest() {
    lateinit var musicViewModel: MusicViewModel
    lateinit var musicRepository: MusicRepository
    @Before
    fun setup() {
        musicRepository = mockk()
        musicViewModel = MusicViewModel(musicRepository, MusicMapper()).apply {
            currentArtist = FakeDataProvider.provideFakeArtist()
            currentAlbum = FakeDataProvider.provideFakeAlbum()
        }
    }

    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    @OptIn(ExperimentalFoundationApi::class)
    @Test
    fun clickOnShowArtistTracksButtonShouldDisplayTracks() {
        lateinit var bottomSheetState: ModalBottomSheetState
        coEvery { musicRepository.getArtistTracks(any()) } returns FakeDataProvider.provideFakeArtistTrackResultList()
        composeTestRule.setContent {
            bottomSheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
            )
            ArtistDetailScreen(
                musicViewModel = musicViewModel,
                navController = rememberAnimatedNavController(),
                snackbarHostState = mockk(),
                artist = musicViewModel.currentArtist, bottomSheetState = bottomSheetState
            )
        }
        composeTestRule.onNodeWithText("Show Artist Tracks").performClick()
        composeTestRule.waitUntil(expression = {
            assert(bottomSheetState.isVisible)
            composeTestRule.onAllNodesWithText("fake artist - fake name", substring = true).assertCountEquals(3)
        }, 3000)
    }

    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    @OptIn(ExperimentalFoundationApi::class)
    @Test
    fun clickOnShowArtistTracksButtonShouldDisplayNoTracksMessage() {
        lateinit var bottomSheetState: ModalBottomSheetState
        coEvery { musicRepository.getArtistTracks(any()) } returns emptyList()
        composeTestRule.setContent {
            bottomSheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
            )
            val scaffoldState = rememberScaffoldState()
            Scaffold(scaffoldState = scaffoldState) {
                ArtistDetailScreen(
                    musicViewModel = musicViewModel,
                    navController = rememberAnimatedNavController(),
                    snackbarHostState = scaffoldState.snackbarHostState,
                    artist = musicViewModel.currentArtist, bottomSheetState = bottomSheetState
                )
            }
        }
        composeTestRule.onNodeWithText("Show Artist Tracks").performClick()
        composeTestRule.waitUntil(expression = {
            assert(!bottomSheetState.isVisible)
            composeTestRule.onNodeWithText(AppConstants.EMPTY_ARTIST_TRACKS_MESSAGE).assertIsDisplayed()
        }, 3000)
    }
    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    @OptIn(ExperimentalFoundationApi::class)
    @Test
    fun clickOnShowAlbumTracksButtonShouldDisplayTracks() {
        lateinit var bottomSheetState: ModalBottomSheetState
        coEvery { musicRepository.getAlbumTracks(any()) } returns FakeDataProvider.provideFakeAlbumTracksResultList()
        composeTestRule.setContent {
            bottomSheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
            )
            AlbumDetailScreen(
                musicViewModel = musicViewModel,
                navController = rememberAnimatedNavController(),
                snackbarHostState = mockk(),
                album = musicViewModel.currentAlbum, bottomSheetState = bottomSheetState
            )
        }
        composeTestRule.onNodeWithText("Show Album Tracks").performClick()
        composeTestRule.waitUntil(expression = {
            assert(bottomSheetState.isVisible)
            composeTestRule.onAllNodesWithText("fake artist - fake name", substring = true).assertCountEquals(3)
        }, 3000)
    }
}