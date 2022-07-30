package com.maks.musicapp.ui.viewmodels

data class RegistrationViewModelStates (
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val image: String = "",
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isRepeatPasswordError: Boolean = false,
)