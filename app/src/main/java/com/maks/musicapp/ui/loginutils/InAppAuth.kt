package com.maks.musicapp.ui.loginutils

import android.content.Context
import android.util.Log
import androidx.compose.material.SnackbarHostState
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.maks.musicapp.R
import com.maks.musicapp.ui.composeutils.navigateFromLoginScreen
import com.maks.musicapp.ui.viewmodels.LoginViewModel
import com.maks.musicapp.utils.Routes
import com.maks.musicapp.utils.showMessage

class InAppAuth(private val context: Context) {
    private val IN_APP_AUTH = "InAppAuth"
     fun signInUser(
        firebaseAuth: FirebaseAuth,
        email: String,
        password: String,
        navController: NavController,
        snackbarHostState: SnackbarHostState,
        loginViewModel: LoginViewModel,
    ) {
        val isInputValid = validateInput(email, password, snackbarHostState, context, loginViewModel)
        if (!isInputValid) return
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navController.navigateFromLoginScreen()
            } else {
                snackbarHostState.showMessage(context.getString(R.string.user_not_exist_message))
            }

        }
    }


     fun signUpUser(
        firebaseAuth: FirebaseAuth,
        email: String,
        password: String,
        navController: NavController,
        snackbarHostState: SnackbarHostState,
        loginViewModel: LoginViewModel,
    ) {
        val isInputValid = validateInput(email, password, snackbarHostState, context, loginViewModel)
        if (!isInputValid) return
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navController.navigateFromLoginScreen()
            } else {
                Log.e(IN_APP_AUTH, "signUpUser: ${task.exception?.localizedMessage}", )

                snackbarHostState.showMessage(context.getString(R.string.failed_auth_message))
            }

        }
    }

    private fun validateInput(
        email: String,
        password: String,
        snackbarHostState: SnackbarHostState,
        context: Context,
        loginViewModel: LoginViewModel
    ): Boolean {
        if (LoginValidator.isEmailOrLoginEmpty(email, password)) {
            snackbarHostState.showMessage(context.getString(R.string.empty_email_or_password))
            return false
        }
        val isNotValidCredentials =
            !LoginValidator.isEmailValid(email) || !LoginValidator.isPasswordValid(password)

        loginViewModel.applyEmailError(!LoginValidator.isEmailValid(email))
        loginViewModel.applyPasswordError(!LoginValidator.isPasswordValid(password))

        if (isNotValidCredentials) {
            return false
        }
        return true
    }
}