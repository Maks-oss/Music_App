package com.maks.musicapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maks.musicapp.data.domain.Track
import com.maks.musicapp.ui.lists.TracksListItem
import com.maks.musicapp.ui.viewmodels.FavouritesViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavouriteTracksScreen(
    favouritesViewModel: FavouritesViewModel,
    trackListItemAction: (Track) -> Unit
) {
    val favouritesTracks = favouritesViewModel.favouritesTracksList
    favouritesViewModel.applyFavouritesTracks()

    favouritesTracks?.let { tracksList ->
        LazyVerticalGrid(columns = GridCells.Fixed(2), state = rememberLazyGridState()) {
            val map = convertToMap(tracksList)
            map.forEach { (key, value) ->
                item(span = { GridItemSpan(maxLineSpan) }) {
                    TracksDivider(searchQuery = key)
                }
                items(value) {
                    TracksListItem(
                        track = it,
                        trackListItemClickAction = trackListItemAction
                    )
                }

            }
        }

    }
}



@Composable
private fun TracksDivider(searchQuery: String) {
    Column(Modifier.padding(8.dp)) {
        Text(text = searchQuery, style = MaterialTheme.typography.h5)
        Divider(modifier = Modifier.padding(vertical = 4.dp))
    }
}

private fun convertToMap(
    tracksList: List<Track>,
): HashMap<String, List<Track>> {
    val map = hashMapOf<String, List<Track>>()
    for (query in tracksList.map { it.searchQuery }) {
        for (track in tracksList) {
            if (track.searchQuery == query) {
                val temp = map.getOrDefault(query, listOf()).toMutableList()
                if (!temp.contains(track)) temp.add(track)
                map[query] = temp
            }
        }
    }
    return map
}