package com.maks.musicapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.maks.musicapp.ui.screens.LoginScreen
import com.maks.musicapp.ui.screens.MainScreen
import com.maks.musicapp.ui.screens.WebViewScreen
import com.maks.musicapp.ui.theme.MusicAppTheme
import com.maks.musicapp.utils.Routes
import com.maks.musicapp.viewmodels.MusicViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

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
        val musicViewModel = getViewModel<MusicViewModel>().apply {
            requestAccessTokenLiveData.observe(this@MainActivity){
                Log.d("TAG", "AppNavigator: ${it.access_token}")
            }
        }
        NavHost(navController = navController, startDestination = Routes.LoginScreenRoute.route) {
            composable(Routes.LoginScreenRoute.route) {
                LoginScreen {
                    navController.navigate(Routes.WebViewScreenRoute.route)
                }
            }
            composable(Routes.WebViewScreenRoute.route) {
                WebViewScreen {
                    if (it.isEmpty()) {
                        navController.navigate(Routes.LoginScreenRoute.route)
                    } else {
                        Log.d("TAG", "AppNavigator: $it")
                        musicViewModel.uploadOauthToken(it)
                        navController.navigate(Routes.MainScreenRoute.route)
                    }
                }
            }
            composable(Routes.MainScreenRoute.route) {
                MainScreen()
            }
        }
    }
}
