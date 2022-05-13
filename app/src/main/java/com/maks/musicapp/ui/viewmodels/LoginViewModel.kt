package com.maks.musicapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var loginViewModelStates: LoginViewModelStates by mutableStateOf(LoginViewModelStates())
        private set

    fun applyLogin(login: String) {
        loginViewModelStates = loginViewModelStates.copy(login = login)
    }

    fun applyPassword(password: String) {
        loginViewModelStates = loginViewModelStates.copy(password = password)

    }

    fun applyRepeatPassword(repeatPassword: String) {
        loginViewModelStates = loginViewModelStates.copy(repeatPassword = repeatPassword)
    }
}