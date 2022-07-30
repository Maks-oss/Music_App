package com.maks.musicapp.firebase.authorization

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.maks.musicapp.utils.LoginValidator

class InAppAuthorization(
    private var firebaseAuth: FirebaseAuth
) {
    private val IN_APP_AUTH = "InAppAuth"
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var repeatPassword: String
    private lateinit var image: String

    fun setUserCredentials(
        email: String,
        password: String,
        repeatPassword: String = "",
        image: String = ""
    ) {
        this.email = email
        this.password = password
        this.repeatPassword = repeatPassword
        this.image = image
    }

    fun signInUser(
        navigateToMainScreen: () -> Unit,
        displayUserNotExistMessage: () -> Unit,
        displayEmptyCredentialsMessage: () -> Unit,
        setInputError: (isValidEmail: Boolean, isValidPassword: Boolean) -> Unit
    ) {
        val isInputValid =
            validateInput(displayEmptyCredentialsMessage, setLoginInputError = setInputError)
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
        navigateToMainScreen: () -> Unit,
        emptyCredentialsMessage: () -> Unit,
        displayAuthFailMessage: () -> Unit,
        setInputError: (isValidEmail: Boolean, isValidPassword: Boolean, isValidRepeat: Boolean) -> Unit
    ) {
        val isInputValid =
            validateInput(emptyCredentialsMessage, setRegistrationInputError = setInputError)
        if (!isInputValid) return
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val firebaseUser = firebaseAuth.currentUser!!
                if (image.isNotEmpty()) {
                    firebaseUser.updateProfile(
                        UserProfileChangeRequest.Builder().setPhotoUri(Uri.parse(image)).build()
                    ).addOnCompleteListener {

                        navigateToMainScreen()
                    }
                } else {
                    navigateToMainScreen()
                }

            } else {
                Log.e(IN_APP_AUTH, "signUpUser: ${task.exception?.localizedMessage}")
                displayAuthFailMessage()
            }

        }
    }

    private fun validateInput(
        displayUserNotExistMessage: () -> Unit,
        setLoginInputError: ((isValidEmail: Boolean, isValidPassword: Boolean) -> Unit)? = null,
        setRegistrationInputError: ((isValidEmail: Boolean, isValidPassword: Boolean, isValidRepeat: Boolean) -> Unit)? = null,
    ): Boolean {
        if (LoginValidator.isEmailOrLoginEmpty(email, password)) {
            displayUserNotExistMessage()
            return false
        }
        val isNotValidCredentials =
            !LoginValidator.isEmailValid(email) || !LoginValidator.isPasswordValid(password)

        if (setLoginInputError != null) {
            setLoginInputError(
                !LoginValidator.isEmailValid(email),
                !LoginValidator.isPasswordValid(password)
            )
        } else if (setRegistrationInputError != null) {
            setRegistrationInputError(
                !LoginValidator.isEmailValid(email),
                !LoginValidator.isPasswordValid(password),
                !LoginValidator.isPasswordsEqual(password, repeatPassword),
            )
        }
        if (isNotValidCredentials) {
            return false
        }
        return true
    }


}