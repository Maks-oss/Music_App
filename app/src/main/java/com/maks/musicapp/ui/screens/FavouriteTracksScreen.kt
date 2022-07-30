package com.maks.musicapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.Composable
import com.maks.musicapp.ui.lists.DisplayTrackList
import com.maks.musicapp.ui.lists.TracksListItem
import com.maks.musicapp.ui.viewmodels.FavouritesViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavouriteTracksScreen(favouritesViewModel: FavouritesViewModel){
    val favouritesTracks = favouritesViewModel.favouritesTracksList
    favouritesViewModel.applyFavouritesTracks()
    DisplayTrackList(tracks = favouritesTracks, scrollState = rememberLazyGridState(), trackListItemClickAction = {})
}