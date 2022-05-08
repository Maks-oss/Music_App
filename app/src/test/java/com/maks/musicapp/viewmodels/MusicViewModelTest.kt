package com.maks.musicapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.google.common.truth.Truth.assertThat
import com.maks.musicapp.MainCoroutineRule
import com.maks.musicapp.fakedata.FakeDataProvider
import com.maks.musicapp.mappers.MusicMapper
import com.maks.musicapp.repository.MusicRepository
import com.maks.musicapp.ui.viewmodels.MusicViewModel
import utils.AsyncTimer
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MusicViewModelTest {

    private lateinit var musicViewModel: MusicViewModel

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        val musicRepository = mockk<MusicRepository>()

        coEvery { musicRepository.getTracksByName(any()) } returns FakeDataProvider.provideFakeTrackResultList()
        coEvery { musicRepository.getAlbumsByName(any()) } returns FakeDataProvider.provideFakeAlbumResultList()
        coEvery { musicRepository.getArtistsByName(any()) } returns FakeDataProvider.provideFakeArtistResultList()

        coEvery { musicRepository.getArtistTracks(any()) } returns FakeDataProvider.provideFakeArtistTrackResultList()
        coEvery { musicRepository.getAlbumTracks(any()) } returns FakeDataProvider.provideFakeAlbumTracksResultList()

        musicViewModel = MusicViewModel(musicRepository, MusicMapper())
    }


    @ExperimentalCoroutinesApi
    @Test
    fun viewModelTrackSearchShouldReturnStateWithTracks() = runTest {
        musicViewModel.findTracksByName()
        delay(2000)
        assertThat(musicViewModel.tracksUiState.isLoading).isFalse()
        assertThat(musicViewModel.tracksUiState.result).isNotEmpty()
        assertThat(musicViewModel.tracksUiState.message).isNull()
    }
    @ExperimentalCoroutinesApi
    @Test
    fun viewModelAlbumSearchShouldReturnStateWithAlbums() = runTest {
        musicViewModel.findAlbumsByName()
        delay(2000)
        assertThat(musicViewModel.albumsUiState.isLoading).isFalse()
        assertThat(musicViewModel.albumsUiState.result).isNotEmpty()
        assertThat(musicViewModel.albumsUiState.message).isNull()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun viewModelArtistSearchShouldReturnStateWithArtists() = runTest {
        musicViewModel.findArtistsByName()
        delay(2000)
        assertThat(musicViewModel.artistsUiState.isLoading).isFalse()
        assertThat(musicViewModel.artistsUiState.result).isNotEmpty()
        assertThat(musicViewModel.artistsUiState.message).isNull()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun viewModelArtistTracksSearchShouldReturnStateWithArtistTracks() = runTest {
        musicViewModel.currentArtist = FakeDataProvider.provideFakeArtist()
        musicViewModel.findArtistsTracks()
        delay(2000)
        assertThat(musicViewModel.artistTracksUiState.isLoading).isFalse()
        assertThat(musicViewModel.artistTracksUiState.result).isNotEmpty()
        assertThat(musicViewModel.artistTracksUiState.message).isNull()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun viewModelAlbumTracksSearchShouldReturnStateWithAlbumTracks() = runTest {
        musicViewModel.currentAlbum = FakeDataProvider.provideFakeAlbum()
        musicViewModel.findAlbumsTracks()
        delay(2000)
        assertThat(musicViewModel.albumTracksUiState.isLoading).isFalse()
        assertThat(musicViewModel.albumTracksUiState.result).isNotEmpty()
        assertThat(musicViewModel.albumTracksUiState.message).isNull()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun viewModelMessageShownMethodsShouldReturnStateWithNoMessages() = runTest {

        musicViewModel.tracksMessageDisplayed()
        musicViewModel.artistsMessageDisplayed()
        musicViewModel.albumsMessageDisplayed()
        musicViewModel.artistTracksMessageDisplayed()
        musicViewModel.albumTracksMessageDisplayed()

        assertThat(musicViewModel.tracksUiState.message).isNull()
        assertThat(musicViewModel.artistsUiState.message).isNull()
        assertThat(musicViewModel.albumsUiState.message).isNull()
        assertThat(musicViewModel.artistTracksUiState.message).isNull()
        assertThat(musicViewModel.albumTracksUiState.message).isNull()
    }
}