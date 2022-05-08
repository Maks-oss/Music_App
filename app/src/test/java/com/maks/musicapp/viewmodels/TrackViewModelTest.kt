package com.maks.musicapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.maks.musicapp.MainCoroutineRule
import com.maks.musicapp.fakedata.FakeDataProvider
import com.maks.musicapp.mappers.MusicMapper
import com.maks.musicapp.repository.MusicRepository
import com.maks.musicapp.ui.viewmodels.MusicViewModel
import com.maks.musicapp.ui.viewmodels.TrackViewModel
import com.maks.musicapp.utils.player.MusicPlayer
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TrackViewModelTest: BaseViewModelTest() {
    private lateinit var trackViewModel: TrackViewModel

    @Before
    fun setup() {
        val musicPlayer = mockk<MusicPlayer>()
        trackViewModel = TrackViewModel()
        every { musicPlayer.play() } returns Unit
        every { musicPlayer.seekTo(any()) } returns Unit
        every { musicPlayer.pause() } returns Unit
        trackViewModel.musicPlayer = musicPlayer

    }

    @Test
    fun trackViewModelPlayTrackShouldStartTrack(){
        trackViewModel.playTrack()
        assertThat(trackViewModel.trackViewModelState.isTrackPlaying).isTrue()
    }

    @Test
    fun trackViewModelStopTrackShouldStopTrack(){
        trackViewModel.playTrack()
        trackViewModel.stopTrack()
        assertThat(trackViewModel.trackViewModelState.isTrackPlaying).isFalse()
    }

    @Test
    fun trackViewModelSeekToTrackShouldUpdateTrackMinutes(){
        trackViewModel.seekTo(2f)
        assertThat(trackViewModel.trackViewModelState.trackMinutes).isEqualTo(2f)
    }

}