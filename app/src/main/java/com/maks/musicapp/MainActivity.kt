package com.maks.musicapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DownloadManager
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.maks.musicapp.firebase.authorization.GoogleAuthorization
import com.maks.musicapp.firebase.authorization.InAppAuthorization
import com.maks.musicapp.ui.broadcastreceivers.TrackDownloadBroadCast
import com.maks.musicapp.ui.composeutils.mainGraph
import com.maks.musicapp.ui.composeutils.navigateFromLoginScreen
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
    private lateinit var googleSignIn: GoogleAuthorization
    private lateinit var inAppAuthorization: InAppAuthorization

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = Firebase.auth
        googleSignIn = GoogleAuthorization(this)
        inAppAuthorization = InAppAuthorization(firebaseAuth)
        Firebase.auth.signOut()
        Log.d(
            "TAG",
            "onCreate: ${firebaseAuth.currentUser?.email} ${firebaseAuth.currentUser?.photoUrl}"
        )
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
        val startForResult =
            rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    googleSignIn.signInGoogle(
                        data = result.data,
                        navigateToMainScreen = {
                            navController.navigateFromLoginScreen()
                        },
                        firebaseAuth = firebaseAuth
                    )
                }
            }

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
                        googleSignIn = {
                            googleSignIn.startGoogleAuthorization(startForResult)
                        },
                        inAppSignIn = { email, password ->
                            inAppAuthorization.setUserCredentials(email, password)
                            inAppAuthorization.signInUser(
                                navigateToMainScreen = { navController.navigateFromLoginScreen() },
                                displayUserNotExistMessage = {
                                    scaffoldState.snackbarHostState.showMessage(
                                        getString(R.string.user_not_exist_message)
                                    )
                                },
                                displayEmptyCredentialsMessage = {
                                    scaffoldState.snackbarHostState.showMessage(
                                        getString(R.string.empty_email_or_password)
                                    )
                                },
                                setInputError = { isValidEmail, isValidPassword ->
                                    loginViewModel.applyEmailError(isValidEmail)
                                    loginViewModel.applyPasswordError(isValidPassword)
                                }
                            )
                        }, inAppSignUp = { email, password ->
                            inAppAuthorization.setUserCredentials(email, password)
                            inAppAuthorization.signUpUser(
                                navigateToMainScreen = { navController.navigateFromLoginScreen() },
                                emptyCredentialsMessage = {
                                    scaffoldState.snackbarHostState.showMessage(
                                        getString(R.string.empty_email_or_password)
                                    )
                                },
                                setInputError = { isValidEmail, isValidPassword ->
                                    loginViewModel.applyEmailError(isValidEmail)
                                    loginViewModel.applyPasswordError(isValidPassword)
                                }, displayAuthFailMessage = {
                                    scaffoldState.snackbarHostState.showMessage(
                                        getString(R.string.failed_auth_message)
                                    )
                                }
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


