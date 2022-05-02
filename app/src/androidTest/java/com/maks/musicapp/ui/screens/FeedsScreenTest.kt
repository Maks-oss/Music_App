package com.maks.musicapp.ui.screens

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.*
import com.maks.musicapp.R
import com.maks.musicapp.fakedata.FakeDataProvider
import com.maks.musicapp.repository.FeedsRepository
import com.maks.musicapp.ui.BaseUiTest
import com.maks.musicapp.ui.theme.MusicAppTheme
import com.maks.musicapp.ui.viewmodels.FeedsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

class FeedsScreenTest : BaseUiTest() {
    lateinit var feedsViewModel: FeedsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        val feedsRepository = mockk<FeedsRepository>()
        coEvery { feedsRepository.getFeeds(any()) } returns FakeDataProvider.provideFakeFeedsList()
        coEvery { feedsRepository.getFeedsFromServer(any()) } returns FakeDataProvider.provideFakeFeedsList(
            isRefresh = true
        )
        coEvery { feedsRepository.insertFeeds(any()) } returns Unit

        feedsViewModel = FeedsViewModel(feedsRepository)


    }

    @Test
    fun feedsCardShouldExpandOnArrowClick() {
        lateinit var expandableCard: String
        composeTestRule.setContent {
            expandableCard = stringResource(id = R.string.expandable_arrow)
            FeedsScreen(feedsViewModel = feedsViewModel)
        }
        composeTestRule.onNodeWithText("artist").performClick()
        composeTestRule.onAllNodesWithContentDescription(expandableCard).onFirst().performClick()
        composeTestRule.onNodeWithText("fake text").assertExists()

        composeTestRule.onAllNodesWithContentDescription(expandableCard).onFirst().performClick()
        composeTestRule.onNodeWithText("fake text").assertDoesNotExist()
    }

    @Test
    fun feedsCardShouldStayExpandedAfterFeedTypeChange() {
        lateinit var expandableCard: String
        composeTestRule.setContent {
            expandableCard = stringResource(id = R.string.expandable_arrow)
            FeedsScreen(feedsViewModel = feedsViewModel)
        }
        composeTestRule.onNodeWithText("artist").performClick()
        composeTestRule.onAllNodesWithContentDescription(expandableCard).onFirst().performClick()
        composeTestRule.onNodeWithText("news").performClick()
        composeTestRule.onNodeWithText("artist").performClick()
        composeTestRule.onNodeWithText("fake text").assertIsDisplayed()
    }

    @Test
    fun swipeRefreshShouldUpdateFeeds() {
        lateinit var feedsColumn: String
        composeTestRule.setContent {
            feedsColumn = stringResource(id = R.string.feeds_column)
            MusicAppTheme {
                FeedsScreen(feedsViewModel = feedsViewModel)
            }
        }
        composeTestRule.onNodeWithText("artist").performClick()
        composeTestRule.onNodeWithContentDescription(feedsColumn).performTouchInput {
            swipeDown()
        }
        composeTestRule.waitUntil(expression = {
            composeTestRule.onNodeWithText("fake refresh title").assertIsDisplayed()
        })
    }
}