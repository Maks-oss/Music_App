package com.maks.musicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.maks.musicapp.ui.screens.LoginScreen
import com.maks.musicapp.ui.screens.MainScreen
import com.maks.musicapp.ui.screens.TrackDetailScreen
import com.maks.musicapp.ui.screens.WebViewScreen
import com.maks.musicapp.ui.theme.MusicAppTheme
import com.maks.musicapp.utils.Routes
import com.maks.musicapp.utils.State
import com.maks.musicapp.viewmodels.AuthorizationViewModel
import com.maks.musicapp.viewmodels.MusicViewModel
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
        val authorizationViewModel = getViewModel<AuthorizationViewModel>()
        val musicViewModel = getViewModel<MusicViewModel>()
        musicViewModel.trackListLiveData.observe(this) {
            when (it.state) {
                State.LOADING -> musicViewModel.musicViewModelStates.setIsLoadingValue(true)
                State.SUCCESS -> musicViewModel.musicViewModelStates.setIsLoadingValue(false)
                State.ERROR -> musicViewModel.musicViewModelStates.setIsLoadingValue(false)
            }
        }
        NavHost(navController = navController, startDestination = Routes.MainScreenRoute.route) {
            composable(Routes.LoginScreenRoute.route) {
                LoginScreen {
                    authorizationViewModel.uploadAccessToken()
                    authorizationViewModel.requestAccessToken.observe(this@MainActivity) { token ->
                        if (token == null) {
                            navController.navigate(Routes.WebViewScreenRoute.route)
                        } else {
                            navController.navigate(Routes.MainScreenRoute.route)
                        }
                    }
                }
            }
            composable(Routes.WebViewScreenRoute.route) {
                WebViewScreen {
                    if (it.isEmpty()) {
                        navController.navigate(Routes.LoginScreenRoute.route)
                    } else {
                        authorizationViewModel.saveOauthToken(it)
                        navController.navigate(Routes.MainScreenRoute.route)
                    }
                }
            }
            composable(Routes.MainScreenRoute.route) {
                MainScreen(musicViewModel,navController)
            }
            composable(Routes.TrackDetailsScreenRoute.route){
                TrackDetailScreen(track = musicViewModel.trackDetail,musicViewModel.musicViewModelStates)
            }
        }
    }

}


