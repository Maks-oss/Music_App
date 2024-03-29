package com.maks.musicapp.utils

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Face
import androidx.compose.ui.graphics.vector.ImageVector
import com.maks.musicapp.R

sealed class Routes(val route: String) {
    object MainGraphRoute: Routes("Main Graph")
    object MainScreenRoute : Routes("MainScreen")

    object FeedsScreenRoute : Routes("FeedsScreen")

    object FavouritesScreenRoute: Routes("FavouritesScreen")

    object TrackDetailsScreenRoute : Routes("TrackDetailsScreenRoute/{navigatedFrom}"){
        fun navigateWithArgument(argument:String) = "TrackDetailsScreenRoute/$argument"
    }
    object ArtistDetailsScreenRoute : Routes("ArtistDetailsScreenRoute")
    object AlbumDetailsScreenRoute : Routes("AlbumDetailsScreenRoute")

    object LoginScreenRoute : Routes("LoginScreenRoute")
    object RegistrationScreenRoute : Routes("RegistrationScreenRoute")
}

sealed class TabRoutes(@StringRes val tabName: Int, val icon: ImageVector) {
    object ArtistsTab : TabRoutes(R.string.artists, Icons.Filled.Face)
    object TracksTab : TabRoutes(R.string.tracks, Icons.Filled.Audiotrack)

    object AlbumsTab : TabRoutes(R.string.albums, Icons.Filled.Album)
}