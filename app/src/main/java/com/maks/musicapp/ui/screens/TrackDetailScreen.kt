package com.maks.musicapp.ui.screens

import android.Manifest
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.*
import com.maks.musicapp.R
import com.maks.musicapp.data.domain.Track
import com.maks.musicapp.data.dto.tracks.Tags
import com.maks.musicapp.ui.composeutils.CustomOutlinedButton
import com.maks.musicapp.ui.viewmodels.TrackViewModel
import com.maks.musicapp.ui.viewmodels.TrackViewModelState
import com.maks.musicapp.utils.*
import com.maks.musicapp.utils.player.MusicPlayer
import com.maks.musicapp.utils.player.MusicPlayerImpl
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun TrackDetailScreen(
    track: Track,
    trackViewModel: TrackViewModel,
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    startService: (Track) -> Unit
) {
    initMusicPlayer(LocalContext.current,track, trackViewModel)
//    BackHandler {
//        navController.navigate(Routes.MainScreenRoute.route)
        startService(track)
//        trackViewModel.setOnBackPressed(true)
//    }
    Surface(
        elevation = 8.dp, shape = MaterialTheme.shapes.medium, modifier = Modifier
            .padding(8.dp)
    ) {
        DisplayTrackDetail(
            track,
            trackViewModel,
            snackbarHostState
        )
    }
}
private fun initMusicPlayer(
    context:Context,
    track: Track,
    trackViewModel: TrackViewModel
) {
    val mediaPlayer = MediaPlayer.create(context, Uri.parse(track.audio))
    val musicPlayer: MusicPlayer = MusicPlayerImpl(mediaPlayer, onTick = {
        trackViewModel.setTrackMinutesValue(mediaPlayer.currentPosition.toFloat())
        if (mediaPlayer.currentPosition == mediaPlayer.duration) {
            trackViewModel.setIsTrackPlayingValue(false)
            trackViewModel.setTrackMinutesValue(0f)
        }
    })
    trackViewModel.musicPlayer = musicPlayer
}

@Composable
fun DisplayTrackDetail(
    track: Track,
    trackViewModel: TrackViewModel,
    snackbarHostState: SnackbarHostState
) {
    val localContext = LocalContext.current
    val trackViewModelState = trackViewModel.trackViewModelState
    if (trackViewModelState.onBackPressed) {
        trackViewModel.stopTrack()
    }
    Column(Modifier.verticalScroll(rememberScrollState())) {
        TrackInfo(track)
        AudioPlayer(
            trackViewModel = trackViewModel,
            downloadTrack = {
                localContext.downloadTrack(track)
            },
            audioPlayerAction = {
                trackViewModel.playTrack()
            },
            showPermissionMessage = { message ->
                snackbarHostState.showMessage(message)
            })
        Spacer(modifier = Modifier.padding(8.dp))
        DisplayMusicInfo(track.musicinfo?.tags)
    }

}

@Composable
fun DisplayMusicInfo(tags: Tags?) {
    tags?.let { tag ->
        Text(text = "Music Genres:", style = MaterialTheme.typography.body2)
        DisplayTags(tags = tag.genres, color = Color.Red)
        Text(text = "Music instruments:", style = MaterialTheme.typography.body2)
        DisplayTags(tags = tag.instruments, color = Color.Blue)
    }
    /** For test purposes */
    /*Column(Modifier.padding(8.dp)) {
        Text(text = "Music Genres:", style = MaterialTheme.typography.body2)
        DisplayTags(tags = listOf("Rock", "Hip-Hop"), color = Color.Red)
        Text(text = "Music instruments:", style = MaterialTheme.typography.body2)
        DisplayTags(tags = listOf("Guitar", "Bass"), color = Color.Blue)
    }*/
}

@Composable
private fun TrackInfo(track: Track) {
    GlideImage(
        imageModel = track.image?.ifEmpty {
            AppConstants.DEFAULT_IMAGE
        },
        contentScale = ContentScale.Crop,
        circularReveal = CircularReveal(),
        placeHolder = ImageBitmap.imageResource(R.drawable.music_logo)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = track.getTrackTitle(),
            style = MaterialTheme.typography.body2
        )
        Text(
            text = track.releasedate
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun AudioPlayer(
    trackViewModel: TrackViewModel,
    audioPlayerAction: () -> Unit,
    downloadTrack: () -> Unit,
    showPermissionMessage: (String) -> Unit
) {
    val trackViewModelState = trackViewModel.trackViewModelState
    Column(modifier = Modifier.padding(8.dp)) {
        Row {
            Icon(
                imageVector = if (trackViewModelState.isTrackPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable(onClick = audioPlayerAction),
                contentDescription = ""
            )
            Slider(
                value = trackViewModelState.trackMinutes,
                modifier = Modifier.padding(horizontal = 8.dp),
                valueRange = 0f..trackViewModel.trackDuration(),
                onValueChange = {
                    trackViewModel.seekTo(it)
                })
        }
        Row {
            Text(text = trackViewModelState.trackMinutes.toMinutes())
            Text(
                text = trackViewModel.trackDuration().toMinutes(),
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        DownloadTrackButton(
            downloadTrack = downloadTrack,
            showPermissionMessage = showPermissionMessage
        )
    }
}

@ExperimentalPermissionsApi
@Composable
fun DownloadTrackButton(downloadTrack: () -> Unit, showPermissionMessage: (String) -> Unit) {
    val filePermissionState = rememberPermissionState(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    var isPermissionDialogShown by remember { mutableStateOf(false) }
    val permissionMessage = stringResource(R.string.external_storage_permission_message)
    CustomOutlinedButton(text = "Download Track", onClick = {
        showPermissionDialog(filePermissionState, showPermissionMessage, permissionMessage)
        isPermissionDialogShown = true
    })

    if (filePermissionState.status.isGranted && isPermissionDialogShown) {
        downloadTrack()
    }
}


@OptIn(ExperimentalPermissionsApi::class)
private fun showPermissionDialog(
    filePermissionState: PermissionState,
    showPermissionMessage: (String) -> Unit,
    permissionMessage: String
) {
    if (!filePermissionState.status.isGranted) {
        if (!filePermissionState.status.shouldShowRationale) {
            showPermissionMessage(permissionMessage)
        }
        filePermissionState.launchPermissionRequest()
    }
}


@Composable
private fun DisplayTags(tags: List<String>, color: Color) {
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        tags.forEach { tag ->
            Text(
                text = tag, modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .background(color.copy(0.4f), CircleShape)
                    .border(
                        2.dp, color,
                        CircleShape
                    )
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)

                        layout(placeable.width * 2, placeable.height * 2) {
                            placeable.placeRelative(placeable.width / 2, placeable.height / 2)
                        }
                    },
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2
            )

        }
    }
}
