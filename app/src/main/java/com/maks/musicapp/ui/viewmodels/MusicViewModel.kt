package com.maks.musicapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maks.musicapp.data.domain.Album
import com.maks.musicapp.data.domain.Artist
import com.maks.musicapp.data.domain.Track
import com.maks.musicapp.mappers.MusicMapper
import com.maks.musicapp.repository.MusicRepository
import com.maks.musicapp.ui.states.AlbumsUiState
import com.maks.musicapp.ui.states.ArtistsUiState
import com.maks.musicapp.ui.states.TracksUiState
import com.maks.musicapp.utils.AppConstants
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class MusicViewModel(
    private val musicRepository: MusicRepository,
    private val musicMapper: MusicMapper
) : ViewModel() {

    var tracksUiState by mutableStateOf(TracksUiState())
        private set

    var artistsUiState by mutableStateOf(ArtistsUiState())
        private set

    var artistTracksUiState by mutableStateOf(TracksUiState())
        private set

    var albumTracksUiState by mutableStateOf(TracksUiState())
        private set

    var albumsUiState by mutableStateOf(AlbumsUiState())
        private set

    var musicViewModelStates by mutableStateOf(MusicViewModelStates())
        private set

    var currentTrack: Track by Delegates.notNull()
    var currentArtist: Artist by Delegates.notNull()
    var currentAlbum: Album by Delegates.notNull()

    fun findTracksByName() {

        tracksUiState = tracksUiState.copy(isLoading = true, tracksResult = null, message = null)
        viewModelScope.launch {
            delay(1000)
            tracksUiState = try {
                val tracksResult = musicRepository.getTracksByName(
                    musicViewModelStates.searchInput
                )
                if (tracksResult.isNullOrEmpty()) {
                    tracksUiState.copy(
                        isLoading = false,
                        tracksResult = null,
                        message = AppConstants.EMPTY_RESULT_MESSAGE
                    )
                } else {
                    tracksUiState.copy(
                        isLoading = false,
                        tracksResult = musicMapper.toTrackList(tracksResult),
                    )
                }

            } catch (exc: Exception) {
                tracksUiState.copy(
                    isLoading = false,
                    tracksResult = null
                )
            }

        }
    }

    fun findArtistsByName() {
        artistsUiState = artistsUiState.copy(isLoading = true, artistsResult = null, message = null)
        viewModelScope.launch {
            delay(1000)
            artistsUiState = try {
                val artistsResult = musicRepository.getArtistsByName(
                    musicViewModelStates.searchInput
                )
                if (artistsResult.isNullOrEmpty()) {
                    artistsUiState.copy(
                        isLoading = false,
                        artistsResult = null,
                        message = AppConstants.EMPTY_RESULT_MESSAGE
                    )
                } else {
                    artistsUiState.copy(
                        isLoading = false,
                        artistsResult = musicMapper.toArtistList(artistsResult)
                    )
                }
            } catch (exc: Exception) {
                artistsUiState.copy(
                    isLoading = false,
                    artistsResult = null,
                    message = exc.localizedMessage
                )
            }
        }
    }

    fun findAlbumsByName() {

        albumsUiState = albumsUiState.copy(isLoading = true, albumsResult = null, message = null)
        viewModelScope.launch {
            delay(1000)
            albumsUiState = try {
                val albumsResult = musicRepository.getAlbumsByName(
                    musicViewModelStates.searchInput
                )
                if (albumsResult.isNullOrEmpty()) {
                    albumsUiState.copy(
                        isLoading = false,
                        albumsResult = null,
                        message = AppConstants.EMPTY_RESULT_MESSAGE
                    )
                } else {
                    albumsUiState.copy(
                        isLoading = false,
                        albumsResult = musicMapper.toAlbumList(albumsResult),
                    )
                }
            } catch (exc: Exception) {
                albumsUiState.copy(
                    isLoading = false,
                    albumsResult = null,
                    message = exc.localizedMessage
                )
            }
        }
    }

    fun findArtistsTracks() {

        artistTracksUiState =
            artistTracksUiState.copy(isLoading = true, tracksResult = null, message = null)
        viewModelScope.launch {
            delay(1000)
            artistTracksUiState = try {
                val artistTracksResult = musicRepository.getArtistTracks(
                    currentArtist.id
                )
                if (artistTracksResult.isNullOrEmpty()) {
                    artistTracksUiState.copy(
                        isLoading = false,
                        tracksResult = null,
                        message = AppConstants.EMPTY_ARTIST_TRACKS_MESSAGE
                    )
                } else {
                    artistTracksUiState.copy(
                        isLoading = false,
                        tracksResult = musicMapper.toArtistTracksList(
                            currentArtist,
                            artistTracksResult
                        )
                    )
                }
            } catch (exc: Exception) {
                artistTracksUiState.copy(
                    isLoading = false,
                    tracksResult = null,
                    message = exc.localizedMessage
                )
            }

        }
    }

    fun findAlbumsTracks() {

        albumTracksUiState =
            albumTracksUiState.copy(isLoading = true, tracksResult = null, message = null)
        viewModelScope.launch {
            delay(1000)
            albumTracksUiState = try {
                val albumTracksResult = musicRepository.getAlbumTracks(
                    currentAlbum.id
                )

                albumTracksUiState.copy(
                    isLoading = false,
                    tracksResult = musicMapper.toAlbumTracksList(
                        currentAlbum,
                        albumTracksResult!!
                    )
                )

            } catch (exc: Exception) {
                albumTracksUiState.copy(
                    isLoading = false,
                    tracksResult = null,
                    message = exc.localizedMessage
                )
            }

        }
    }


    fun tracksMessageDisplayed() {
        tracksUiState = tracksUiState.copy(message = null)
    }

    fun artistsMessageDisplayed() {
        artistsUiState = artistsUiState.copy(message = null)
    }

    fun albumsMessageDisplayed() {
        albumsUiState = albumsUiState.copy(message = null)
    }

    fun artistTracksMessageDisplayed() {
        artistTracksUiState = artistTracksUiState.copy(message = null)
    }

    fun albumTracksMessageDisplayed() {
        albumTracksUiState = albumTracksUiState.copy(message = null)
    }

    fun setIsTrackPlayingValue(value: Boolean) {
        musicViewModelStates = musicViewModelStates.copy(isTrackPlaying = value)
    }

    fun setTrackMinutesValue(value: Float) {
        musicViewModelStates = musicViewModelStates.copy(trackMinutes = value)
    }

    fun setCurrentJobValue(value: Job?) {
        musicViewModelStates = musicViewModelStates.copy(currentJob = value)
    }

    fun setTabStateValue(value: Int) {
        musicViewModelStates = musicViewModelStates.copy(tabState = value)
    }

    fun setSearchInputValue(value: String) {
        musicViewModelStates = musicViewModelStates.copy(searchInput = value)
    }

    fun setTextFieldVisibilityValue(value: Boolean) {
        musicViewModelStates = musicViewModelStates.copy(textFieldVisibility = value)
    }


}