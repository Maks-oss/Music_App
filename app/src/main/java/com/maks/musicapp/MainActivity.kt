package com.maks.musicapp

import android.app.DownloadManager
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarHostState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.maks.musicapp.firebase.authorization.GoogleAuthorization
import com.maks.musicapp.firebase.authorization.InAppAuthorization
import com.maks.musicapp.ui.broadcastreceivers.TrackDownloadBroadCast
import com.maks.musicapp.ui.theme.MusicAppTheme
import com.maks.musicapp.utils.showMessage

@ExperimentalMaterialApi
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    private var trackDownloadBroadCast: TrackDownloadBroadCast? = null
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignIn: GoogleAuthorization
    private lateinit var inAppAuthorization: InAppAuthorization

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = Firebase.auth
        googleSignIn = GoogleAuthorization(this)
        inAppAuthorization = InAppAuthorization(firebaseAuth)
//        Firebase.auth.signOut()
        Log.d(
            "TAG",
            "onCreate: ${firebaseAuth.currentUser?.email} ${firebaseAuth.currentUser?.photoUrl}"
        )
        setContent {
            MusicAppTheme {
                AppNavigator(
                    googleSignIn = googleSignIn,
                    inAppAuthorization = inAppAuthorization,
                    firebaseAuth = firebaseAuth,
                    registerTrackDownloadBroadcastReceiver = {
                        registerTrackDownloadBroadcastReceiver(
                            it
                        )
                    })
            }

        }
    }

    override fun onStop() {
        super.onStop()
        trackDownloadBroadCast?.let { broadCast ->
            unregisterReceiver(broadCast)
        }
    }


    private fun registerTrackDownloadBroadcastReceiver(
        snackbarHostState: SnackbarHostState,
    ) {
        trackDownloadBroadCast = TrackDownloadBroadCast(showMessage = { message ->
            snackbarHostState.showMessage(message)
        })
        registerReceiver(
            trackDownloadBroadCast,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }


}


