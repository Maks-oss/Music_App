package com.maks.musicapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maks.musicapp.data.music.TrackResult
import com.maks.musicapp.repository.TrackRepository
import kotlinx.coroutines.launch

class TrackViewModel(private val trackRepository: TrackRepository) : ViewModel() {
    private val _trackListLiveData: MutableLiveData<List<TrackResult>> = MutableLiveData()
    private val trackListLiveData: LiveData<List<TrackResult>> = _trackListLiveData

    val searchFieldValue = mutableStateOf("")
    fun findTracksByName(name: String) {
        viewModelScope.launch {
            _trackListLiveData.postValue(trackRepository.getTracksByName(name))
        }
    }
}