package com.maks.musicapp.firebase.authorization

import android.content.Context
import android.util.Log
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.maks.musicapp.ui.composeutils.navigateFromLoginScreen
import com.maks.musicapp.ui.viewmodels.LoginViewModel
import com.maks.musicapp.utils.LoginValidator

class InAppAuthorization(
    private var firebaseAuth: FirebaseAuth
) {
    private val IN_APP_AUTH = "InAppAuth"
    private lateinit var email: String
    private lateinit var password: String

    fun setUserCredentials(email: String, password: String) {
        this.email = email
        this.password = password
    }

    fun signInUser(
        navigateToMainScreen:()->Unit,
        displayUserNotExistMessage: () -> Unit,
        displayEmptyCredentialsMessage: () -> Unit,
        setInputError: (isValidEmail:Boolean, isValidPassword:Boolean)->Unit
    ) {
        val isInputValid =
            validateInput(displayEmptyCredentialsMessage, setInputError)
        if (!isInputValid) return
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navigateToMainScreen()
            } else {
                Log.e(IN_APP_AUTH, "signInUser: ${task.exception?.localizedMessage}")
                displayUserNotExistMessage()
            }

        }
    }


    fun signUpUser(
        navigateToMainScreen:()->Unit,
        emptyCredentialsMessage: () -> Unit,
        displayAuthFailMessage: () -> Unit,
        setInputError: (isValidEmail:Boolean, isValidPassword:Boolean)->Unit
    ) {
        val isInputValid =
            validateInput(emptyCredentialsMessage, setInputError)
        if (!isInputValid) return
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navigateToMainScreen()
            } else {
                Log.e(IN_APP_AUTH, "signUpUser: ${task.exception?.localizedMessage}")
                displayAuthFailMessage()
            }

        }
    }

    private fun validateInput(
        displayUserNotExistMessage: () -> Unit,
        setInputError: (isValidEmail:Boolean, isValidPassword:Boolean)->Unit
    ): Boolean {
        if (LoginValidator.isEmailOrLoginEmpty(email, password)) {
            displayUserNotExistMessage()
            return false
        }
        val isNotValidCredentials =
            !LoginValidator.isEmailValid(email) || !LoginValidator.isPasswordValid(password)

        setInputError(!LoginValidator.isEmailValid(email),!LoginValidator.isPasswordValid(password))
        if (isNotValidCredentials) {
            return false
        }
        return true
    }




}