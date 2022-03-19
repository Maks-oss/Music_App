package com.maks.musicapp.utils

sealed class Routes(val route:String) {
    object MainScreenRoute :Routes("MainScreen")
    object WebViewScreenRoute :Routes("WebViewScreen")
    object LoginScreenRoute :Routes("LoginScreenRoute")
}