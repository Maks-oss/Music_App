package com.maks.musicapp.ui.loginutils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.maks.musicapp.MainActivity
import com.maks.musicapp.R
import com.maks.musicapp.utils.Routes

class GoogleSignIn(private val activity: Activity) {
    val oneTapClient: SignInClient = Identity.getSignInClient(activity)
    val signInRequest: BeginSignInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(activity.getString(R.string.server_client_id))
                .setFilterByAuthorizedAccounts(true)
                .build()
        )
        .build()
    private val GOOGLE_AUTH_TAG = "GoogleAuth"

    fun signInGoogle(firebaseAuth: FirebaseAuth,navController:NavController,data: Intent?) {
        try {
            val credential = oneTapClient.getSignInCredentialFromIntent(data)
            val idToken = credential.googleIdToken
            when {
                idToken != null -> {
                    processAuthorization(idToken, firebaseAuth, navController)
                }
                else -> {
                    Log.e(GOOGLE_AUTH_TAG, "Id Token is null")
                }
            }
        } catch (e: ApiException) {
            Log.e(GOOGLE_AUTH_TAG, e.localizedMessage)
        }
    }


    private fun processAuthorization(
        idToken: String?,
        firebaseAuth: FirebaseAuth,
        navController: NavController
    ) {
        val firebaseCredential =
            GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(firebaseCredential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    navController.navigate(Routes.MainGraphRoute.route) {
                        popUpTo(Routes.LoginScreenRoute.route) {
                            inclusive = true
                        }
                    }
                }
                Log.d(
                    GOOGLE_AUTH_TAG,
                    "registerActivityForResult: ${task.result}"
                )
            }
    }
}