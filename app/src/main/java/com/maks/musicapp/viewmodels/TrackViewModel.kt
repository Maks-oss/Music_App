package com.maks.musicapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maks.musicapp.data.music.TrackResult
import com.maks.musicapp.repository.TrackRepository
import com.maks.musicapp.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TrackViewModel(private val trackRepository: TrackRepository) : ViewModel() {
    private val _trackListLiveData: MutableLiveData<Resource<List<TrackResult>>> = MutableLiveData()
    val trackListLiveData: LiveData<Resource<List<TrackResult>>> = _trackListLiveData

    val isLoading = mutableStateOf(false)

    val searchFieldValue = mutableStateOf("")
    fun findTracksByName(name: String) {
        _trackListLiveData.value = Resource.loading(null)
        viewModelScope.launch {
            delay(1000)
            _trackListLiveData.postValue(Resource.success(trackRepository.getTracksByName(name)))
        }
    }

    fun setIsLoadingValue(value: Boolean) {
        isLoading.value = value
    }
}