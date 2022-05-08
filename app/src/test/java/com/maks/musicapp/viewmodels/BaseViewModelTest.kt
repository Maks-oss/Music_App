package com.maks.musicapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.maks.musicapp.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

open class BaseViewModelTest {
    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
}