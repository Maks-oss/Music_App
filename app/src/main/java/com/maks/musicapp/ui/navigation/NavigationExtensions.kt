package com.maks.musicapp.ui.composeutils

import android.annotation.SuppressLint
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
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.maks.musicapp.ui.screens.FavouriteTracksScreen
import com.maks.musicapp.ui.screens.FeedsScreen
import com.maks.musicapp.ui.screens.MainScreen
import com.maks.musicapp.ui.viewmodels.FeedsViewModel
import com.maks.musicapp.ui.viewmodels.MusicViewModel
import com.maks.musicapp.utils.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
fun NavGraphBuilder.mainGraph(
    firebaseAuth: FirebaseAuth,
    navController: NavController,
    musicViewModel: MusicViewModel,
    feedsViewModel: FeedsViewModel,
    scaffoldState: ScaffoldState,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope
) {
    val springSpec = spring<IntOffset>(dampingRatio = Spring.DampingRatioMediumBouncy)
    navigation(Routes.MainScreenRoute.route, Routes.MainGraphRoute.route) {
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
                feedsViewModel = feedsViewModel,
                user = firebaseAuth.currentUser,
                userSignOut = {
                    navController.navigate(Routes.LoginScreenRoute.route){
                        popUpTo(Routes.MainGraphRoute.route){
                            inclusive = true
                        }
                    }
                }
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
                user = firebaseAuth.currentUser,
                drawerState = drawerState,
                navController = navController,
                musicViewModel = musicViewModel,
                feedsViewModel = feedsViewModel,
                userSignOut = {
                    navController.navigate(Routes.LoginScreenRoute.route){
                        popUpTo(Routes.MainGraphRoute.route){
                            inclusive = true
                        }
                    }
                }
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
        composable(Routes.FavouritesScreenRoute.route, enterTransition = {
            slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = springSpec)
        }, exitTransition = {
            slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = springSpec)
        }, popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = springSpec)
        }, popExitTransition = {
            slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = springSpec)
        }) {
            MusicModalDrawer(
                user = firebaseAuth.currentUser,
                drawerState = drawerState,
                navController = navController,
                musicViewModel = musicViewModel,
                feedsViewModel = feedsViewModel,
                userSignOut = {
                    navController.navigate(Routes.LoginScreenRoute.route){
                        popUpTo(Routes.MainGraphRoute.route){
                            inclusive = true
                        }
                    }
                }
            ) {
                Scaffold(scaffoldState = scaffoldState,
                    topBar = {
                        MusicTopAppBar(navigationIconClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        })
                    }) {
                    FavouriteTracksScreen(getViewModel(), trackListItemAction = { track ->
                        musicViewModel.currentTrack = track
                        navController.navigate(Routes.TrackDetailsScreenRoute.navigateWithArgument(Routes.FavouritesScreenRoute.route))
                    })
                }
            }
        }
    }
}

fun NavController.navigateFromLoginScreen(){
    navigate(Routes.MainGraphRoute.route) {
        popUpTo(Routes.LoginScreenRoute.route) {
            inclusive = true
        }
    }
}
