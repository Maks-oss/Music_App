package com.maks.musicapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.maks.musicapp.data.domain.Track
import com.maks.musicapp.firebase.database.FirebaseDatabaseUtil

class FavouritesViewModel : ViewModel() {
    var favouritesTracksList: List<Track>? by mutableStateOf(emptyList())
        private set

    fun applyFavouritesTracks() {
        FirebaseDatabaseUtil.addTracksValueListener {
            favouritesTracksList = it
        }
    }
}