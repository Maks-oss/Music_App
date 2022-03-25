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
        val musicViewModel = getViewModel<AuthorizationViewModel>()
        val trackViewModel = getViewModel<MusicViewModel>()
        trackViewModel.trackListLiveData.observe(this) {
            when (it.state) {
                State.LOADING -> trackViewModel.musicViewModelStates.setIsLoadingValue(true)
                State.SUCCESS -> trackViewModel.musicViewModelStates.setIsLoadingValue(false)
                State.ERROR -> trackViewModel.musicViewModelStates.setIsLoadingValue(false)
            }
        }
        NavHost(navController = navController, startDestination = Routes.LoginScreenRoute.route) {
            composable(Routes.LoginScreenRoute.route) {
                LoginScreen {
                    musicViewModel.uploadAccessToken()
                    musicViewModel.requestAccessToken.observe(this@MainActivity) { token ->
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
                        musicViewModel.saveOauthToken(it)
                        navController.navigate(Routes.MainScreenRoute.route)
                    }
                }
            }
            composable(Routes.MainScreenRoute.route) {
                MainScreen(trackViewModel)
            }
        }
    }
}
