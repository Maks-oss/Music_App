package com.maks.musicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.maks.musicapp.ui.screens.MainScreen
import com.maks.musicapp.ui.screens.TrackDetailScreen
import com.maks.musicapp.ui.theme.MusicAppTheme
import com.maks.musicapp.utils.Resource
import com.maks.musicapp.utils.Routes
import com.maks.musicapp.utils.State
import com.maks.musicapp.viewmodels.MusicViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

@ExperimentalMaterialApi
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MusicAppTheme {
                AppNavigator()
            }

        }
    }


    @Composable
    fun AppNavigator() {

        val navController = rememberNavController()
        val musicViewModel = getViewModel<MusicViewModel>()
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        musicViewModel.setupObservers(showSnackBar = { value ->
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(value, actionLabel = "OK")
            }
        })

        NavHost(navController = navController, startDestination = Routes.MainScreenRoute.route) {

            composable(Routes.MainScreenRoute.route) {
                Scaffold(scaffoldState = scaffoldState) {
                    MainScreen(musicViewModel, navController)
                }
            }
            composable(Routes.TrackDetailsScreenRoute.route) {
                TrackDetailScreen(
                    track = musicViewModel.trackDetail,
                    musicViewModel.musicViewModelStates
                )

            }
        }
    }

    private fun MusicViewModel.setupObservers(showSnackBar: (String) -> Unit) {
        trackListLiveData.observe(this@MainActivity) {
            processLiveData(it, showSnackBar)
        }
        albumsListLiveData.observe(this@MainActivity) {
            processLiveData(it, showSnackBar)
        }
        artistListLiveData.observe(this@MainActivity) {
            processLiveData(it, showSnackBar)
        }
    }


    private fun <T> MusicViewModel.processLiveData(
        resource: Resource<List<T>>,
        showSnackBar: (String) -> Unit
    ) {
        when (resource.state) {
            State.LOADING -> musicViewModelStates.setIsLoadingValue(true)
            State.SUCCESS -> {
                musicViewModelStates.setIsLoadingValue(false)
                if (resource.value.isNullOrEmpty() && resource.tabIndex == musicViewModelStates.tabState.value) {
                    showSnackBar(getString(R.string.empty_request))
                }
            }
            State.ERROR -> musicViewModelStates.setIsLoadingValue(false)
        }
    }

}


