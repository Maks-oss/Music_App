package com.maks.musicapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var loginViewModelStates: LoginViewModelStates by mutableStateOf(LoginViewModelStates())
        private set

    fun applyEmail(login: String) {
        loginViewModelStates = loginViewModelStates.copy(email = login)
        applyEmailError(false)
    }
    fun applyPasswordVisibility(isVisible: Boolean){
        loginViewModelStates = loginViewModelStates.copy(isPasswordHidden = isVisible)
    }
    fun applyPassword(password: String) {
        loginViewModelStates = loginViewModelStates.copy(password = password)
        applyPasswordError(false)
    }
    fun applyEmailError(emailError: Boolean){
        loginViewModelStates = loginViewModelStates.copy(isEmailError = emailError)
    }
    fun applyPasswordError(passwordError: Boolean){
        loginViewModelStates = loginViewModelStates.copy(isPasswordError = passwordError)
    }
    fun applyRepeatPassword(repeatPassword: String) {
        loginViewModelStates = loginViewModelStates.copy(repeatPassword = repeatPassword)
    }
}