package com.maks.musicapp.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.maks.musicapp.repository.MusicRepository
import com.maks.musicapp.ui.screens.AlbumDetailScreen
import com.maks.musicapp.ui.screens.ArtistDetailScreen
import com.maks.musicapp.ui.screens.MainScreen
import com.maks.musicapp.ui.screens.TrackDetailScreen
import com.maks.musicapp.ui.viewmodels.MusicViewModel
import com.maks.musicapp.ui.viewmodels.TrackViewModel
import com.maks.musicapp.utils.Routes
import io.mockk.mockk

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TestNavHostController(musicViewModel: MusicViewModel,navController: NavHostController){
//    val navController = rememberAnimatedNavController()

    AnimatedNavHost(navController = navController, startDestination = Routes.MainScreenRoute.route){
        composable(Routes.MainScreenRoute.route){
            MainScreen(musicViewModel = musicViewModel, navController = navController, snackbarHostState = mockk())
        }
        composable(Routes.TrackDetailsScreenRoute.route){
            TrackDetailScreen(musicViewModel.currentTrack, TrackViewModel(),mockk())
        }
        composable(Routes.ArtistDetailsScreenRoute.route){
            ArtistDetailScreen(musicViewModel, navController = navController,mockk(),musicViewModel.currentArtist)
        }
        composable(Routes.AlbumDetailsScreenRoute.route){
            AlbumDetailScreen(musicViewModel = musicViewModel, navController = navController, snackbarHostState = mockk(),album = musicViewModel.currentAlbum)
        }
    }
}