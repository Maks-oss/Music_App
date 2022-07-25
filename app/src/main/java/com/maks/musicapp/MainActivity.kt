package com.maks.musicapp

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.IntentFilter
import android.content.IntentSender
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.maks.musicapp.ui.broadcastreceivers.TrackDownloadBroadCast
import com.maks.musicapp.ui.composeutils.mainGraph
import com.maks.musicapp.ui.loginutils.GoogleSignIn
import com.maks.musicapp.ui.loginutils.InAppSignIn
import com.maks.musicapp.ui.screens.AlbumDetailScreen
import com.maks.musicapp.ui.screens.ArtistDetailScreen
import com.maks.musicapp.ui.screens.LoginScreen
import com.maks.musicapp.ui.screens.TrackDetailScreen
import com.maks.musicapp.ui.theme.MusicAppTheme
import com.maks.musicapp.ui.viewmodels.FeedsViewModel
import com.maks.musicapp.ui.viewmodels.LoginViewModel
import com.maks.musicapp.ui.viewmodels.MusicViewModel
import com.maks.musicapp.ui.viewmodels.TrackViewModel
import com.maks.musicapp.utils.Routes
import com.maks.musicapp.utils.showMessage
import org.koin.androidx.viewmodel.ext.android.getViewModel

@ExperimentalMaterialApi
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    private var trackDownloadBroadCast: TrackDownloadBroadCast? = null
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignIn: GoogleSignIn
    private lateinit var inAppSignIn: InAppSignIn

    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
    private val GOOGLE_AUTH_TAG = " GoogleAuth"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = Firebase.auth
        googleSignIn = GoogleSignIn(this)
        inAppSignIn = InAppSignIn(this)
//        Log.d("TAG", "onCreate: ${firebaseAuth.currentUser}")
        setContent {
            MusicAppTheme {
                AppNavigator()
            }

        }
    }

    override fun onStop() {
        super.onStop()
        trackDownloadBroadCast?.let { broadCast ->
            unregisterReceiver(broadCast)
        }
    }

    private fun startGoogleAuthorization(navController: NavController) {
        googleSignIn.oneTapClient.beginSignIn(googleSignIn.signInRequest)
            .addOnSuccessListener(this) { result ->
                try {
                    val activityResultLauncher = registerActivityForResult(navController)
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    activityResultLauncher.launch(intentSenderRequest)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e(GOOGLE_AUTH_TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(this) { e ->
                Log.d(GOOGLE_AUTH_TAG, e.localizedMessage)
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

    private fun registerActivityForResult(navController: NavController): ActivityResultLauncher<IntentSenderRequest> {
        return registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            when (result.resultCode) {
                REQ_ONE_TAP -> googleSignIn.signInGoogle(
                    firebaseAuth = firebaseAuth,
                    navController = navController,
                    data = result.data
                )
            }
        }
    }


    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalAnimationApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun AppNavigator() {

        val navController = rememberAnimatedNavController()
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        val musicViewModel = getViewModel<MusicViewModel>()
        val trackViewModel = getViewModel<TrackViewModel>()
        val feedsViewModel = getViewModel<FeedsViewModel>()
        val loginViewModel = getViewModel<LoginViewModel>()
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val startDestination =
            if (firebaseAuth.currentUser != null) Routes.MainGraphRoute.route else Routes.LoginScreenRoute.route
        registerTrackDownloadBroadcastReceiver(scaffoldState.snackbarHostState)

        AnimatedNavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(Routes.LoginScreenRoute.route) {
                Scaffold(scaffoldState = scaffoldState) {
                    LoginScreen(
                        loginViewModel = loginViewModel,
                        googleSignIn = { startGoogleAuthorization(navController) },
                        inAppSignIn = { email, password ->
                            inAppSignIn.signInUser(
                                firebaseAuth,
                                email,
                                password,
                                navController,
                                scaffoldState.snackbarHostState,
                                loginViewModel
                            )
                        }, inAppSignUp = { email, password ->
                            inAppSignIn.signUpUser(
                                firebaseAuth,
                                email,
                                password,
                                navController,
                                scaffoldState.snackbarHostState,
                                loginViewModel
                            )
                        }
                    )
                }
            }

            mainGraph(
                navController,
                musicViewModel,
                feedsViewModel,
                scaffoldState,
                drawerState,
                coroutineScope
            )

            composable(Routes.TrackDetailsScreenRoute.route) {
                Scaffold(scaffoldState = scaffoldState) {
                    TrackDetailScreen(
                        track = musicViewModel.currentTrack,
                        trackViewModel = trackViewModel,
                        snackbarHostState = scaffoldState.snackbarHostState,
                    )
                }

            }
            composable(Routes.ArtistDetailsScreenRoute.route) {
                Scaffold(scaffoldState = scaffoldState) {
                    ArtistDetailScreen(
                        artist = musicViewModel.currentArtist,
                        navController = navController,
                        snackbarHostState = scaffoldState.snackbarHostState,
                        musicViewModel = musicViewModel
                    )
                }

            }

            composable(Routes.AlbumDetailsScreenRoute.route) {
                Scaffold(scaffoldState = scaffoldState) {
                    AlbumDetailScreen(
                        album = musicViewModel.currentAlbum,
                        musicViewModel = musicViewModel,
                        navController = navController,
                        snackbarHostState = scaffoldState.snackbarHostState
                    )
                }

            }

        }
    }


}


