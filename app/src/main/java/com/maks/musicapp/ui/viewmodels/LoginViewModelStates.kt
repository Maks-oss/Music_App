package com.maks.musicapp.ui.viewmodels

data class LoginViewModelStates(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isPasswordHidden: Boolean = true
)