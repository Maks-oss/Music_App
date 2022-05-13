package com.maks.musicapp

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.auth.FirebaseAuth
import com.maks.musicapp.ui.broadcastreceivers.TrackDownloadBroadCast
import com.maks.musicapp.ui.composeutils.MusicModalDrawer
import com.maks.musicapp.ui.composeutils.MusicTopAppBar
import com.maks.musicapp.ui.screens.*
import com.maks.musicapp.ui.theme.MusicAppTheme
import com.maks.musicapp.ui.viewmodels.FeedsViewModel
import com.maks.musicapp.ui.viewmodels.LoginViewModel
import com.maks.musicapp.ui.viewmodels.MusicViewModel
import com.maks.musicapp.ui.viewmodels.TrackViewModel
import com.maks.musicapp.utils.Routes
import com.maks.musicapp.utils.showMessage
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

@ExperimentalMaterialApi
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    private var trackDownloadBroadCast: TrackDownloadBroadCast? = null
    lateinit var firebaseAuth: FirebaseAuth
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        setContent {
            MusicAppTheme {
                AppNavigator()
//                ModalDrawer()
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
        registerReceiver(
            trackDownloadBroadCast,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }


    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalAnimationApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun AppNavigator() {

        val navController = rememberAnimatedNavController()
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        val musicViewModel = getViewModel<MusicViewModel>()
        val trackViewModel = getViewModel<TrackViewModel>()
        val feedsViewModel = getViewModel<FeedsViewModel>()
        val loginViewModel = getViewModel<LoginViewModel>()
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        registerTrackDownloadBroadcastReceiver(scaffoldState.snackbarHostState)
        val springSpec = spring<IntOffset>(dampingRatio = Spring.DampingRatioMediumBouncy)
        AnimatedNavHost(
            navController = navController,
            startDestination = Routes.LoginScreenRoute.route
        ) {
            composable(Routes.LoginScreenRoute.route){
                LoginScreen(loginViewModel = loginViewModel)
            }

            composable(Routes.MainScreenRoute.route, enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = springSpec)
            }, exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = springSpec)
            }, popEnterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = springSpec)
            }, popExitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = springSpec)
            }) {
                MusicModalDrawer(
                    drawerState = drawerState,
                    navController = navController,
                    musicViewModel = musicViewModel,
                    feedsViewModel = feedsViewModel
                ) {
                    Scaffold(scaffoldState = scaffoldState,
                        topBar = {
                            MusicTopAppBar(navigationIconClick = {
                                coroutineScope.launch {
                                    drawerState.open()
                                }
                            })
                        }) {
                        MainScreen(
                            musicViewModel = musicViewModel,
                            navController = navController,
                            snackbarHostState = scaffoldState.snackbarHostState,
                        )
                    }
                }
            }
            composable(Routes.FeedsScreenRoute.route, enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = springSpec)
            }, exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = springSpec)
            }, popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = springSpec)
            }, popExitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = springSpec)
            }) {
                MusicModalDrawer(
                    drawerState = drawerState,
                    navController = navController,
                    musicViewModel = musicViewModel,
                    feedsViewModel = feedsViewModel
                ) {
                    Scaffold(scaffoldState = scaffoldState,
                        topBar = {
                            MusicTopAppBar(navigationIconClick = {
                                coroutineScope.launch {
                                    drawerState.open()
                                }
                            })
                        }) {
                        FeedsScreen(feedsViewModel)
                    }
                }
            }

            composable(Routes.TrackDetailsScreenRoute.route) {
                Scaffold(scaffoldState = scaffoldState) {
                    TrackDetailScreen(
                        track = musicViewModel.currentTrack,
                        trackViewModel = trackViewModel,
                        snackbarHostState = scaffoldState.snackbarHostState,
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


