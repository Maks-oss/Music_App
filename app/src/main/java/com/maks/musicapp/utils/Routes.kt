package com.maks.musicapp.utils

import android.graphics.drawable.Drawable
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Face
import androidx.compose.ui.graphics.vector.ImageVector
import com.maks.musicapp.R

sealed class Routes(val route:String) {
    object MainScreenRoute :Routes("MainScreen")
    object WebViewScreenRoute :Routes("WebViewScreen")
    object LoginScreenRoute :Routes("LoginScreenRoute")
}
sealed class TabRoutes(val route:String,@StringRes val tabName:Int,val icon:ImageVector){
    object ArtistsTab:TabRoutes("ArtistsScreen", R.string.artists,Icons.Filled.Face)
    object TracksTab:TabRoutes("TracksScreen", R.string.tracks,Icons.Filled.Audiotrack)
}