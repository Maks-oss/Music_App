package com.maks.musicapp.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maks.musicapp.data.music.artist.ArtistResult
import com.maks.musicapp.data.music.track.TrackResult
import com.maks.musicapp.repository.MusicRepository
import com.maks.musicapp.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MusicViewModel(private val musicRepository: MusicRepository) : ViewModel() {

    private val _trackListLiveData: MutableLiveData<Resource<List<TrackResult>>> = MutableLiveData()
    val trackListLiveData: LiveData<Resource<List<TrackResult>>> = _trackListLiveData

    private val _artistListLiveData: MutableLiveData<Resource<List<ArtistResult>>> =
        MutableLiveData()
    val artistListLiveData: LiveData<Resource<List<ArtistResult>>> = _artistListLiveData

    val musicViewModelStates = MusicViewModelStates()

    class MusicViewModelStates {
        val isLoading = mutableStateOf(false)
        val searchName = mutableStateOf("")
        var currentJob = mutableStateOf<Job?>(null)
        val tabState = mutableStateOf(0)
        val textFieldVisibility = mutableStateOf(true)


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

        fun setTextFieldVisibilityValue(value: Boolean){
            textFieldVisibility.value=value
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
                    )
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
                    )
                )
            )
        }
    }


}