package com.maks.musicapp.viewmodels

import com.google.common.truth.Truth.assertThat
import com.maks.musicapp.fakedata.FakeDataProvider
import com.maks.musicapp.mappers.MusicMapper
import com.maks.musicapp.repository.MusicRepository
import com.maks.musicapp.ui.viewmodels.MusicViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MusicViewModelTest: BaseViewModelTest() {

    private lateinit var musicViewModel: MusicViewModel
    private lateinit var musicRepository: MusicRepository
    @Before
    fun setup() {
        musicRepository = mockk()

        coEvery { musicRepository.getTracksByName(any()) } returns FakeDataProvider.provideFakeTrackResultList()
        coEvery { musicRepository.getAlbumsByName(any()) } returns FakeDataProvider.provideFakeAlbumResultList()
        coEvery { musicRepository.getArtistsByName(any()) } returns FakeDataProvider.provideFakeArtistResultList()

        coEvery { musicRepository.getArtistTracks(any()) } returns FakeDataProvider.provideFakeArtistTrackResultList()
        coEvery { musicRepository.getAlbumTracks(any()) } returns FakeDataProvider.provideFakeAlbumTracksResultList()

        musicViewModel = MusicViewModel(musicRepository, MusicMapper())
    }


    @ExperimentalCoroutinesApi
    @Test
    fun musicViewModelTrackSearchShouldReturnStateWithTracks() = runTest {
        musicViewModel.findTracksByName()
        delay(2000)
        assertThat(musicViewModel.tracksUiState.isLoading).isFalse()
        assertThat(musicViewModel.tracksUiState.result).isNotEmpty()
        assertThat(musicViewModel.tracksUiState.message).isNull()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun musicViewModelTrackSearchShouldReturnStateWithEmptyTracksResult() = runTest {
        coEvery { musicRepository.getTracksByName(any()) } returns null
        musicViewModel.findTracksByName()
        delay(2000)
        assertThat(musicViewModel.tracksUiState.isLoading).isFalse()
        assertThat(musicViewModel.tracksUiState.result).isNull()
        assertThat(musicViewModel.tracksUiState.message).isNotNull()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun musicViewModelAlbumSearchShouldReturnStateWithAlbums() = runTest {
        musicViewModel.findAlbumsByName()
        delay(2000)
        assertThat(musicViewModel.albumsUiState.isLoading).isFalse()
        assertThat(musicViewModel.albumsUiState.result).isNotEmpty()
        assertThat(musicViewModel.albumsUiState.message).isNull()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun musicViewModelAlbumSearchShouldReturnStateWithEmptyAlbumsResult() = runTest {
        coEvery { musicRepository.getAlbumsByName(any()) } returns null
        musicViewModel.findAlbumsByName()
        delay(2000)
        assertThat(musicViewModel.albumsUiState.isLoading).isFalse()
        assertThat(musicViewModel.albumsUiState.result).isNull()
        assertThat(musicViewModel.albumsUiState.message).isNotNull()
    }
    @ExperimentalCoroutinesApi
    @Test
    fun musicViewModelArtistSearchShouldReturnStateWithArtists() = runTest {
        musicViewModel.findArtistsByName()
        delay(2000)
        assertThat(musicViewModel.artistsUiState.isLoading).isFalse()
        assertThat(musicViewModel.artistsUiState.result).isNotEmpty()
        assertThat(musicViewModel.artistsUiState.message).isNull()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun musicViewModelArtistSearchShouldReturnStateWithEmptyArtistsResult() = runTest {
        coEvery { musicRepository.getArtistsByName(any()) } returns null
        musicViewModel.findArtistsByName()
        delay(2000)
        assertThat(musicViewModel.artistsUiState.isLoading).isFalse()
        assertThat(musicViewModel.artistsUiState.result).isNull()
        assertThat(musicViewModel.artistsUiState.message).isNotNull()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun musicViewModelArtistTracksSearchShouldReturnStateWithArtistTracks() = runTest {
        musicViewModel.currentArtist = FakeDataProvider.provideFakeArtist()
        musicViewModel.findArtistsTracks()
        delay(2000)
        assertThat(musicViewModel.artistTracksUiState.isLoading).isFalse()
        assertThat(musicViewModel.artistTracksUiState.result).isNotEmpty()
        assertThat(musicViewModel.artistTracksUiState.message).isNull()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun musicViewModelArtistTracksSearchShouldReturnStateWithNoTracks() = runTest {
        coEvery { musicRepository.getArtistTracks(any()) } returns null
        musicViewModel.currentArtist = FakeDataProvider.provideFakeArtist()
        musicViewModel.findArtistsTracks()
        delay(2000)
        assertThat(musicViewModel.artistTracksUiState.isLoading).isFalse()
        assertThat(musicViewModel.artistTracksUiState.result).isNull()
        assertThat(musicViewModel.artistTracksUiState.message).isNotEmpty()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun musicViewModelAlbumTracksSearchShouldReturnStateWithAlbumTracks() = runTest {
        musicViewModel.currentAlbum = FakeDataProvider.provideFakeAlbum()
        musicViewModel.findAlbumsTracks()
        delay(2000)
        assertThat(musicViewModel.albumTracksUiState.isLoading).isFalse()
        assertThat(musicViewModel.albumTracksUiState.result).isNotEmpty()
        assertThat(musicViewModel.albumTracksUiState.message).isNull()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun musicViewModelMessageShownMethodsShouldReturnStateWithNoMessages() = runTest {

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