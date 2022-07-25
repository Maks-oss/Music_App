package com.maks.musicapp.ui.loginutils

import android.util.Patterns

object LoginValidator {
    fun isEmailOrLoginEmpty(email:String, password: String):Boolean = email.isEmpty() || password.isEmpty()
    fun isEmailValid(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun isPasswordValid(password: String): Boolean = password.length > 3
}