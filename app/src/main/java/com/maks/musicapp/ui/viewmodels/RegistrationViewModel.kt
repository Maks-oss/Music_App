package com.maks.musicapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegistrationViewModel: ViewModel() {
    var registrationViewModelStates by mutableStateOf(RegistrationViewModelStates())
        private set

    fun applyEmail(value:String){
        registrationViewModelStates = registrationViewModelStates.copy(email = value)
        applyEmailError(false)
    }
    fun applyPassword(value:String){
        registrationViewModelStates = registrationViewModelStates.copy(password = value)
        applyPasswordError(false)
    }
    fun applyRepeatPassword(value:String){
        registrationViewModelStates = registrationViewModelStates.copy(repeatPassword = value)
        applyRepeatPasswordError(false)
    }

    fun applyImage(value:String){
        registrationViewModelStates = registrationViewModelStates.copy(image = value)
    }
    fun applyPasswordError(value:Boolean){
        registrationViewModelStates = registrationViewModelStates.copy(isPasswordError = value)
    }
    fun applyRepeatPasswordError(value:Boolean){
        registrationViewModelStates = registrationViewModelStates.copy(isRepeatPasswordError = value)
    }
    fun applyEmailError(value:Boolean){
        registrationViewModelStates = registrationViewModelStates.copy(isEmailError = value)
    }

}