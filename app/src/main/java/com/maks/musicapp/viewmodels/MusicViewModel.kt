package com.maks.musicapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maks.musicapp.data.RequestAccessToken
import com.maks.musicapp.repository.SpotifyAuthorizationRepository
import kotlinx.coroutines.launch

class MusicViewModel(private val spotifyAuthorizationRepository: SpotifyAuthorizationRepository):ViewModel() {
    private val _requestAccessTokenLiveData: MutableLiveData<RequestAccessToken> = MutableLiveData()
    val requestAccessTokenLiveData:LiveData<RequestAccessToken> = _requestAccessTokenLiveData

    fun uploadOauthToken(code:String){
        viewModelScope.launch {
            _requestAccessTokenLiveData.postValue(spotifyAuthorizationRepository.getAuthorizationToken(code))
        }
    }
}