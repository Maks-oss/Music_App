package com.maks.musicapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maks.musicapp.data.music.albums.AlbumResult
import com.maks.musicapp.data.music.artist.ArtistResult
import com.maks.musicapp.data.music.track.TrackResult
import com.maks.musicapp.repository.MusicRepository
import com.maks.musicapp.utils.Resource
import com.maks.musicapp.utils.TabRowConstants
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class MusicViewModel(private val musicRepository: MusicRepository) : ViewModel() {

    private val _trackListLiveData: MutableLiveData<Resource<List<TrackResult>>> = MutableLiveData()
    val trackListLiveData: LiveData<Resource<List<TrackResult>>> = _trackListLiveData

    private val _artistListLiveData: MutableLiveData<Resource<List<ArtistResult>>> =
        MutableLiveData()
    val artistListLiveData: LiveData<Resource<List<ArtistResult>>> = _artistListLiveData

    private val _albumsListLiveData: MutableLiveData<Resource<List<AlbumResult>>> =
        MutableLiveData()
    val albumsListLiveData: LiveData<Resource<List<AlbumResult>>> = _albumsListLiveData

    val musicViewModelStates = MusicViewModelStates()

    var trackDetail: TrackResult by Delegates.notNull()
    var artistDetail: ArtistResult by Delegates.notNull()
    var albumDetail: AlbumResult by Delegates.notNull()

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
        _trackListLiveData.value = Resource.loading(null)
        viewModelScope.launch {
            delay(1000)
            _trackListLiveData.postValue(
                Resource.success(
                    musicRepository.getTracksByName(
                        musicViewModelStates.searchName.value
                    ),TabRowConstants.TRACK_TAB_INDEX
                )
            )
        }
    }

    fun findArtistsByName() {
        _artistListLiveData.value = Resource.loading(null)
        viewModelScope.launch {
            delay(1000)
            _artistListLiveData.postValue(
                Resource.success(
                    musicRepository.getArtistsByName(
                        musicViewModelStates.searchName.value
                    ),TabRowConstants.ARTIST_TAB_INDEX
                )
            )
        }
    }

    fun findAlbumsByName() {
        _albumsListLiveData.value = Resource.loading(null)
        viewModelScope.launch {
            delay(1000)
            _albumsListLiveData.postValue(
                Resource.success(
                    musicRepository.getAlbumsByName(
                        musicViewModelStates.searchName.value
                    ),TabRowConstants.ALBUM_TAB_INDEX
                )
            )
        }
    }


}