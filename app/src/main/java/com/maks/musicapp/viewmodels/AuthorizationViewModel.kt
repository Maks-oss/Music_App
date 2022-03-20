package com.maks.musicapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maks.musicapp.repository.AuthorizationRepository
import com.maks.musicapp.repository.DataStoreRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AuthorizationViewModel(
    private val authorizationRepository: AuthorizationRepository,
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    private val requestAccessTokenLiveData: MutableLiveData<String> = MutableLiveData()
    val requestAccessToken: LiveData<String?> = requestAccessTokenLiveData

    fun saveOauthToken(code: String) {
        viewModelScope.launch {
            dataStoreRepository.setAccessToken(authorizationRepository.getAuthorizationToken(code).access_token)
        }
    }

    fun uploadAccessToken() {
        viewModelScope.launch {
            dataStoreRepository.retrieveAccessToken().collect {
                requestAccessTokenLiveData.postValue(it)
            }
        }
    }
}