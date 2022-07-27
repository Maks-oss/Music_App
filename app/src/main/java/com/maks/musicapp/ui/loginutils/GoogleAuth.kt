package com.maks.musicapp.ui.loginutils

//import android.R

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.maks.musicapp.R


class GoogleAuth(private val activity: Activity) {

    private val oneTapClient: SignInClient = Identity.getSignInClient(activity)
    private val signInRequest: BeginSignInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(activity.getString(R.string.server_client_id))
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .build()
    private val GOOGLE_AUTH_TAG = "GoogleAuth"

    fun signInGoogle(firebaseAuth: FirebaseAuth,data: Intent?,navigateToMainScreen:()->Unit) {
        try {
            val credential = oneTapClient.getSignInCredentialFromIntent(data)
            val idToken = credential.googleIdToken
            when {
                idToken != null -> {
                    processAuthorization(idToken, firebaseAuth,navigateToMainScreen)
                }
                else -> {
                    Log.e(GOOGLE_AUTH_TAG, "Id Token is null")
                }
            }

//            val credentials = Identity.getSignInClient(activity).getSignInCredentialFromIntent(data)
//            navigateToMainScreen()

        } catch (e: ApiException) {
            Log.e(GOOGLE_AUTH_TAG, e.localizedMessage)
        }
    }

    fun startGoogleAuthorization(requestActivityResultLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>) {

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(activity) { result ->
                try {

                    val intentSenderRequest =
                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    requestActivityResultLauncher.launch(intentSenderRequest)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e(GOOGLE_AUTH_TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(activity) { e ->
                Log.d(GOOGLE_AUTH_TAG, e.localizedMessage)
            }

    }

    private fun processAuthorization(
        idToken: String?,
        firebaseAuth: FirebaseAuth,
        navigateToMainScreen: () -> Unit
    ) {
        val firebaseCredential =
            GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(firebaseCredential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful){
                    navigateToMainScreen()
                }
                Log.d(
                    GOOGLE_AUTH_TAG,
                    "registerActivityForResult: ${task.result.user}"
                )
            }

    }

}