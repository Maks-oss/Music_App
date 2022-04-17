package com.maks.musicapp

import android.app.DownloadManager
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.maks.musicapp.services.MusicForegroundService
import com.maks.musicapp.ui.broadcastreceivers.TrackDownloadBroadCast
import com.maks.musicapp.ui.composeutils.MusicTopAppBar
import com.maks.musicapp.ui.screens.AlbumDetailScreen
import com.maks.musicapp.ui.screens.ArtistDetailScreen
import com.maks.musicapp.ui.screens.MainScreen
import com.maks.musicapp.ui.screens.TrackDetailScreen
import com.maks.musicapp.ui.theme.MusicAppTheme
import com.maks.musicapp.ui.viewmodels.MusicViewModel
import com.maks.musicapp.ui.viewmodels.TrackViewModel
import com.maks.musicapp.utils.Routes
import com.maks.musicapp.utils.showMessage
import org.koin.androidx.viewmodel.ext.android.getViewModel

@ExperimentalMaterialApi
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    private var trackDownloadBroadCast: TrackDownloadBroadCast? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicAppTheme {
                AppNavigator()
            }

        }
    }

    override fun onStop() {
        super.onStop()
        trackDownloadBroadCast?.let { broadCast ->
            unregisterReceiver(broadCast)
        }
    }

    private fun registerTrackDownloadBroadcastReceiver(
        snackbarHostState: SnackbarHostState,
    ) {
        trackDownloadBroadCast = TrackDownloadBroadCast(showMessage = { message ->
            snackbarHostState.showMessage(message)
        })
        registerReceiver(trackDownloadBroadCast,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }


    @Composable
    fun AppNavigator() {

        val navController = rememberNavController()
        val musicViewModel = getViewModel<MusicViewModel>()
        val scaffoldState = rememberScaffoldState()
        val trackViewModel = getViewModel<TrackViewModel>()
        registerTrackDownloadBroadcastReceiver(scaffoldState.snackbarHostState)
        NavHost(navController = navController, startDestination = Routes.MainScreenRoute.route) {
            composable(Routes.MainScreenRoute.route) {
                Scaffold(scaffoldState = scaffoldState, topBar = { MusicTopAppBar() }) {
                    MainScreen(
                        musicViewModel = musicViewModel,
                        navController = navController,
                        snackbarHostState = scaffoldState.snackbarHostState
                    )
                }
            }

            composable(Routes.TrackDetailsScreenRoute.route) {
                Scaffold(scaffoldState = scaffoldState) {
                    TrackDetailScreen(
                        track = musicViewModel.currentTrack,
                        trackViewModel = trackViewModel,
                        navController = navController,
                        snackbarHostState = scaffoldState.snackbarHostState,
                        startService = {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                startForegroundService(Intent(this@MainActivity,MusicForegroundService::class.java).apply {
                                    putExtra("track",it)
                                })
                            }
                        }
                    )
                }

            }
            composable(Routes.ArtistDetailsScreenRoute.route) {
                Scaffold(scaffoldState = scaffoldState) {
                    ArtistDetailScreen(
                        artist = musicViewModel.currentArtist,
                        navController = navController,
                        snackbarHostState = scaffoldState.snackbarHostState,
                        musicViewModel = musicViewModel
                    )
                }

            }
            composable(Routes.AlbumDetailsScreenRoute.route) {
                Scaffold(scaffoldState = scaffoldState) {
                    AlbumDetailScreen(
                        album = musicViewModel.currentAlbum,
                        musicViewModel = musicViewModel,
                        navController = navController,
                        snackbarHostState = scaffoldState.snackbarHostState
                    )
                }

            }

        }
    }


}


