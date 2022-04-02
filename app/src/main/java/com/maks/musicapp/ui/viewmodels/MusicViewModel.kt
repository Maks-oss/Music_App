package com.maks.musicapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maks.musicapp.data.music.albums.AlbumResult
import com.maks.musicapp.data.music.artist.ArtistResult
import com.maks.musicapp.data.music.track.TrackResult
import com.maks.musicapp.repository.MusicRepository
import com.maks.musicapp.ui.states.AlbumsUiState
import com.maks.musicapp.ui.states.ArtistsUiState
import com.maks.musicapp.ui.states.TracksUiState
import com.maks.musicapp.utils.AppConstants
import com.maks.musicapp.utils.toTrackResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class MusicViewModel(private val musicRepository: MusicRepository) : ViewModel() {

    //    private val _trackListLiveData: MutableLiveData<Resource<List<TrackResult>>> = MutableLiveData()
//    val trackListLiveData: LiveData<Resource<List<TrackResult>>> = _trackListLiveData
    var tracksUiState by mutableStateOf(TracksUiState())
        private set

    //    private val _artistListLiveData: MutableLiveData<Resource<List<ArtistResult>>> =
//        MutableLiveData()
//    val artistListLiveData: LiveData<Resource<List<ArtistResult>>> = _artistListLiveData
    var artistsUiState by mutableStateOf(ArtistsUiState())
        private set

    //    private val _artistTracksListLiveData: MutableLiveData<Resource<List<TrackResult>>> =
//        MutableLiveData()
//    val artistTracksListLiveData: LiveData<Resource<List<TrackResult>>> = _artistTracksListLiveData
    var artistTracksUiState by mutableStateOf(TracksUiState())
        private set

//    private val _albumsListLiveData: MutableLiveData<Resource<List<AlbumResult>>> =
//        MutableLiveData()
//    val albumsListLiveData: LiveData<Resource<List<AlbumResult>>> = _albumsListLiveData

    var albumsUiState by mutableStateOf(AlbumsUiState())
        private set

    val musicViewModelStates = MusicViewModelStates()

    var currentTrack: TrackResult by Delegates.notNull()
    var currentArtist: ArtistResult by Delegates.notNull()
    var currentAlbum: AlbumResult by Delegates.notNull()

    class MusicViewModelStates {
        val isLoading = mutableStateOf(false)
        val searchName = mutableStateOf("")
        val currentJob = mutableStateOf<Job?>(null)
        val tabState = mutableStateOf(0)
        val textFieldVisibility = mutableStateOf(true)

        val isTrackPlaying = mutableStateOf(false)
        val trackMinutes = mutableStateOf(0F)

        fun setIsTrackPlayingValue(value: Boolean) {
            isTrackPlaying.value = value
        }

        fun setTrackMinutesValue(value: Float) {
            trackMinutes.value = value
        }

        fun setIsLoadingValue(value: Boolean) {
            isLoading.value = value
        }

        fun setCurrentJobValue(value: Job?) {
            currentJob.value = value
        }

        fun setTabStateValue(value: Int) {
            tabState.value = value
        }

        fun setSearchNameValue(value: String) {
            searchName.value = value
        }

        fun setTextFieldVisibilityValue(value: Boolean) {
            textFieldVisibility.value = value
        }
    }


    fun findTracksByName() {

        tracksUiState = tracksUiState.copy(isLoading = true, tracksResult = null, message = null)
        viewModelScope.launch {
            delay(1000)
            tracksUiState = try {
                val tracks = musicRepository.getTracksByName(
                    musicViewModelStates.searchName.value
                )
                if (tracks.isNullOrEmpty()) {
                    tracksUiState.copy(
                        isLoading = false,
                        tracksResult = null,
                        message = AppConstants.EMPTY_RESULT_MESSAGE
                    )
                } else {
                    tracksUiState.copy(
                        isLoading = false,
                        tracksResult = tracks,
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
                    musicViewModelStates.searchName.value
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
                        artistsResult = artistsResult
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
                    musicViewModelStates.searchName.value
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
                        albumsResult = albumsResult,
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
                val artistTracks = musicRepository.getArtistTracks(
                    currentArtist.id
                )
                if (artistTracks.isNullOrEmpty()) {
                    artistTracksUiState.copy(
                        isLoading = false,
                        tracksResult = null,
                        message = AppConstants.EMPTY_ARTIST_TRACKS_MESSAGE
                    )
                } else {
                    artistTracksUiState.copy(
                        isLoading = false,
                        tracksResult = artistTracks.map { it.toTrackResult() }
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


    fun tracksMessageDisplayed() {
        tracksUiState = tracksUiState.copy(message = null)
    }

    fun artistsMessageDisplayed() {
        artistsUiState = artistsUiState.copy(message = null)
    }

    fun albumsMessageDisplayed() {
        albumsUiState = albumsUiState.copy(message = null)
    }


}