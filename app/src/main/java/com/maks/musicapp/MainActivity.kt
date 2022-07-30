package com.maks.musicapp

import android.app.DownloadManager
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarHostState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.maks.musicapp.firebase.authorization.GoogleAuthorization
import com.maks.musicapp.firebase.authorization.InAppAuthorization
import com.maks.musicapp.firebase.database.FirebaseDatabaseUtil
import com.maks.musicapp.ui.broadcastreceivers.TrackDownloadBroadCast
import com.maks.musicapp.ui.theme.MusicAppTheme
import com.maks.musicapp.utils.showMessage
import androidx.core.app.ActivityCompat.startActivityForResult

import android.content.Intent
import android.util.Log


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
//        val element = Track("1", "album 1", "1", "artist 2", "2", "1", 1, "2", "1", null, "1", "1")
//        val testUser = User("test maks", "pass")
//        firebaseDatabase.child("users/${testUser.hashCode()}").setValue(testUser)
//        firebaseDatabase.child("users/${testUser.hashCode()}/tracks/${element.hashCode()}").setValue(element)
//        Firebase.auth.signOut()
        FirebaseDatabaseUtil.setDatabaseReference(Firebase.database.reference)
//        firebaseAuth.currentUser?.let { user ->
//            FirebaseDatabaseUtil.setCurrentUserId(user.email.hashCode().toString())
//        }
//        FirebaseDatabaseUtil.addUserNewFavouriteTrack(element)
//        FirebaseDatabaseUtil.addTracksValueListener{}
//        FirebaseDatabaseUtil.deleteNewUserFavouriteTrack(element.id)
//        Log.d("TAG", "onCreate: ${FirebaseDatabaseUtil.getUser(testUser.hashCode())}")
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
                    }, unRegisterTrackDownloadBroadCast = {
                        trackDownloadBroadCast?.let { broadCast ->
                            unregisterReceiver(broadCast)
                        }
                    })
            }

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


