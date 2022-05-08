package com.maks.musicapp.viewmodels

import com.google.common.truth.Truth.assertThat
import com.maks.musicapp.fakedata.FakeDataProvider
import com.maks.musicapp.repository.FeedsRepository
import com.maks.musicapp.ui.viewmodels.FeedsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class FeedsViewModelTest: BaseViewModelTest() {
    lateinit var feedsViewModel: FeedsViewModel

    @Before
    fun setup(){
        val feedsRepository = mockk<FeedsRepository>()
        coEvery { feedsRepository.getFeeds(any()) } returns FakeDataProvider.provideFakeFeedsList()
        coEvery { feedsRepository.insertFeeds(any()) } returns Unit
        coEvery { feedsRepository.getFeedsFromServer(any()) } returns FakeDataProvider.provideFakeFeedsList()
        feedsViewModel = FeedsViewModel(feedsRepository)
    }
    @Test
    fun feedsViewModelFeedSearchShouldReturnStateWithFeeds(){
        feedsViewModel.applyFeeds()
        assertThat(feedsViewModel.feedsUiState.isLoading).isFalse()
        assertThat(feedsViewModel.feedsUiState.message).isNull()
        assertThat(feedsViewModel.feedsUiState.result).isNotEmpty()
    }
    @Test
    fun feedsViewModelFeedSearchRefreshShouldReturnStateWithFeeds(){
        feedsViewModel.applyFeeds(isRefresh = true)
        assertThat(feedsViewModel.feedsUiState.isLoading).isFalse()
        assertThat(feedsViewModel.feedsUiState.message).isNull()
        assertThat(feedsViewModel.feedsUiState.result).isNotEmpty()
    }
}