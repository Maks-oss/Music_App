package com.maks.musicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.maks.musicapp.ui.screens.ArtistDetailScreen
import com.maks.musicapp.ui.screens.MainScreen
import com.maks.musicapp.ui.screens.TrackDetailScreen
import com.maks.musicapp.ui.theme.MusicAppTheme
import com.maks.musicapp.utils.Routes
import com.maks.musicapp.ui.viewmodels.MusicViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

@ExperimentalMaterialApi
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MusicAppTheme {
                AppNavigator()
//                TracksBottomSheetLayout()
            }

        }
    }


    @Composable
    fun AppNavigator() {

        val navController = rememberNavController()
        val musicViewModel = getViewModel<MusicViewModel>()
        val scaffoldState = rememberScaffoldState()


        NavHost(navController = navController, startDestination = Routes.MainScreenRoute.route) {

            composable(Routes.MainScreenRoute.route) {
                Scaffold(scaffoldState = scaffoldState) {
                    MainScreen(musicViewModel, navController,scaffoldState.snackbarHostState)
                }
            }
            composable(Routes.TrackDetailsScreenRoute.route) {
                TrackDetailScreen(
                    track = musicViewModel.currentTrack,
                    musicViewModel.musicViewModelStates,
                    navController
                )
            }
            composable(Routes.ArtistDetailsScreenRoute.route) {
                Scaffold(scaffoldState = scaffoldState) {

                    ArtistDetailScreen(
                        artistResult = musicViewModel.currentArtist,
                        navController = navController,
                        snackbarHostState = scaffoldState.snackbarHostState,
                        musicViewModel = musicViewModel
                    )
                }

            }

        }
    }


}


