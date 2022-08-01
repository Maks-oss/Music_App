package com.maks.musicapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.auth.FirebaseAuth
import com.maks.musicapp.data.domain.Track
import com.maks.musicapp.firebase.authorization.GoogleAuthorization
import com.maks.musicapp.firebase.authorization.InAppAuthorization
import com.maks.musicapp.services.MusicForegroundService
import com.maks.musicapp.ui.composeutils.mainGraph
import com.maks.musicapp.ui.composeutils.navigateFromLoginScreen
import com.maks.musicapp.ui.screens.*
import com.maks.musicapp.ui.viewmodels.*
import com.maks.musicapp.utils.Routes
import com.maks.musicapp.utils.showMessage
import org.koin.androidx.compose.getViewModel

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigator(
    googleSignIn: GoogleAuthorization,
    inAppAuthorization: InAppAuthorization,
    firebaseAuth: FirebaseAuth,
    registerTrackDownloadBroadcastReceiver: (SnackbarHostState) -> Unit,
    unRegisterTrackDownloadBroadCast: ()->Unit,
    startForegroundService: (Track,isPlaying: Boolean) -> Unit,
    stopForegroundService: () -> Unit
) {

    val navController = rememberAnimatedNavController()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val musicViewModel = getViewModel<MusicViewModel>()
    val trackViewModel = getViewModel<TrackViewModel>()
    val feedsViewModel = getViewModel<FeedsViewModel>()
    val loginViewModel = getViewModel<LoginViewModel>()
    val registrationViewModel = getViewModel<RegistrationViewModel>()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val lifecycleOwner = LocalLifecycleOwner.current
    val startIntentSenderForResult =
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
    val startActivityForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val image = result.data?.data
                registrationViewModel.applyImage(image.toString())
            }
        }
    val context = LocalContext.current

    val startDestination =
        if (firebaseAuth.currentUser != null) Routes.MainGraphRoute.route else Routes.LoginScreenRoute.route

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                unRegisterTrackDownloadBroadCast()
            } else if (event == Lifecycle.Event.ON_RESUME) {
                registerTrackDownloadBroadcastReceiver(scaffoldState.snackbarHostState)
            }

        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.LoginScreenRoute.route) {
            Scaffold(scaffoldState = scaffoldState) {
                LoginScreen(
                    loginViewModel = loginViewModel,
                    googleSignIn = {
                        googleSignIn.startGoogleAuthorization(startIntentSenderForResult)
                    },
                    inAppSignIn = { email, password ->
                        inAppAuthorization.setUserCredentials(email, password)
                        inAppAuthorization.signInUser(
                            navigateToMainScreen = { navController.navigateFromLoginScreen() },
                            displayUserNotExistMessage = {
                                scaffoldState.snackbarHostState.showMessage(
                                    context.getString(R.string.user_not_exist_message)
                                )
                            },
                            displayEmptyCredentialsMessage = {
                                scaffoldState.snackbarHostState.showMessage(
                                    context.getString(R.string.empty_email_or_password)
                                )
                            },
                            setInputError = { isValidEmail, isValidPassword ->
                                loginViewModel.applyEmailError(isValidEmail)
                                loginViewModel.applyPasswordError(isValidPassword)
                            }
                        )
                    },
                    navigateToRegistrationScreen = {
                        navController.navigate(Routes.RegistrationScreenRoute.route)
                    }
                )
            }
        }
        composable(Routes.RegistrationScreenRoute.route){
            Scaffold(scaffoldState = scaffoldState) {
                RegistrationScreen(
                    registrationViewModel = registrationViewModel,
                    selectImage = {
                        startActivityForResult.launch(Intent(Intent.ACTION_GET_CONTENT).apply {
                            type = "image/*"
                        })
                    },
                    inAppSignUp = {
                        val registrationViewModelStates =
                            registrationViewModel.registrationViewModelStates
                        inAppAuthorization.setUserCredentials(
                            registrationViewModelStates.email,
                            registrationViewModelStates.password,
                            registrationViewModelStates.repeatPassword,
                            registrationViewModelStates.image
                        )
                        inAppAuthorization.signUpUser(
                            navigateToMainScreen = { navController.navigateFromLoginScreen() },
                            emptyCredentialsMessage = {
                                scaffoldState.snackbarHostState.showMessage(
                                    context.getString(R.string.empty_email_or_password)
                                )
                            },
                            setInputError = { isValidEmail, isValidPassword, isValidRepeatPassword ->
                                registrationViewModel.apply {
                                    applyEmailError(isValidEmail)
                                    applyPasswordError(isValidPassword)
                                    applyRepeatPasswordError(isValidRepeatPassword)
                                }
                            }, displayAuthFailMessage = { message ->
                                scaffoldState.snackbarHostState.showMessage(
                                    message
                                )
                            }
                        )
                    },
                )
            }
        }

        mainGraph(
            firebaseAuth,
            navController,
            musicViewModel,
            feedsViewModel,
            scaffoldState,
            drawerState,
            coroutineScope
        )
        composable(Routes.TrackDetailsScreenRoute.route) { backstackEntry ->
            Scaffold(scaffoldState = scaffoldState) {
                TrackDetailScreen(
                    track = musicViewModel.currentTrack,
                    trackViewModel = trackViewModel,
                    snackbarHostState = scaffoldState.snackbarHostState,
                    navigatedFrom = backstackEntry.arguments?.getString("navigatedFrom")?:"",
                    startService = startForegroundService,
                    stopService = stopForegroundService
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