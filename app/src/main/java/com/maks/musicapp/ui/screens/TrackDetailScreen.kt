package com.maks.musicapp.ui.screens

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.maks.musicapp.R
import com.maks.musicapp.data.domain.Track
import com.maks.musicapp.data.dto.tracks.Tags
import com.maks.musicapp.ui.composeutils.CustomOutlinedButton
import com.maks.musicapp.ui.viewmodels.MusicViewModel
import com.maks.musicapp.utils.AppConstants
import com.maks.musicapp.utils.Routes
import com.maks.musicapp.utils.TrackCountDownTimer
import com.maks.musicapp.utils.toMinutes
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@Composable
fun TrackDetailScreen(
    track: Track,
    musicViewModel: MusicViewModel,
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    val mediaPlayer = MediaPlayer.create(LocalContext.current, Uri.parse(track.audio))
    BackHandler {
        navController.navigate(Routes.MainScreenRoute.route)
        musicViewModel.setTrackMinutesValue(0f)
        musicViewModel.setOnBackPressed(true)
    }

    Surface(
        elevation = 8.dp, shape = MaterialTheme.shapes.medium, modifier = Modifier
            .padding(8.dp)
    ) {
        DisplayTrackDetail(
            track,
            mediaPlayer,
            musicViewModel,
            snackbarHostState
        )
    }
}

@Composable
fun DisplayTrackDetail(
    track: Track,
    mediaPlayer: MediaPlayer,
    musicViewModel: MusicViewModel,
    snackbarHostState: SnackbarHostState
) {
    val musicViewModelStates = musicViewModel.musicViewModelStates
    val localContext = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val trackCountDownTimer = TrackCountDownTimer(
        mediaPlayer.duration.toLong(),
        musicViewModel = musicViewModel,
        mediaPlayer = mediaPlayer
    )
    if (musicViewModel.musicViewModelStates.onBackPressed){
        trackCountDownTimer.cancel()
    }
    Column(Modifier.verticalScroll(rememberScrollState())) {
        TrackInfo(track)
        AudioPlayer(mediaPlayer = mediaPlayer, musicViewModel = musicViewModel, downloadTrack = {
            val downloadManager = DownloadManager.Request(Uri.parse(track.audio)).apply {
                setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    track.name.plus(".mp4")
                )
                setTitle(track.name)
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            }
            startTrackDownload(localContext, downloadManager, showMessage = { message ->
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(message,actionLabel = localContext.getString(R.string.ok))
                }
            })
        }, audioPlayerAction = {
            if (musicViewModelStates.isTrackPlaying) {
                mediaPlayer.pause()
            } else {
                mediaPlayer.start()
            }
            musicViewModel.setIsTrackPlayingValue(!musicViewModelStates.isTrackPlaying)
            trackCountDownTimer.start()
        })

        Spacer(modifier = Modifier.padding(8.dp))
        DisplayMusicInfo(track.musicinfo?.tags)

    }

}

private fun startTrackDownload(
    localContext: Context,
    downloadManager: DownloadManager.Request,
    showMessage: (String) -> Unit
) {
    val manager: DownloadManager =
        localContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    manager.enqueue(downloadManager)
    val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            showMessage("Download Completed!")
        }
    }
    localContext.registerReceiver(
        broadcastReceiver,
        IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
    )
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
            text = buildString {
                append(track.artist_name)
                append(" -\n")
                append(track.name)
            },
            style = MaterialTheme.typography.body2
        )
        Text(
            text = track.releasedate
        )
    }
}

@Composable
private fun AudioPlayer(
    mediaPlayer: MediaPlayer,
    musicViewModel: MusicViewModel,
    audioPlayerAction: () -> Unit,
    downloadTrack: () -> Unit
) {
    val musicViewModelStates = musicViewModel.musicViewModelStates
    Column(modifier = Modifier.padding(8.dp)) {
        Row {
            Icon(
                imageVector = if (musicViewModelStates.isTrackPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable(onClick = audioPlayerAction),
                contentDescription = ""
            )
            Slider(
                value = musicViewModelStates.trackMinutes,
                modifier = Modifier.padding(horizontal = 8.dp),
                valueRange = 0f..mediaPlayer.duration.toFloat(),
                onValueChange = {
                    musicViewModel.setTrackMinutesValue(it)
                    mediaPlayer.seekTo(it.toInt())
                })
        }
        Row {
            Text(text = musicViewModelStates.trackMinutes.toMinutes())
            Text(
                text = mediaPlayer.duration.toMinutes(),
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        CustomOutlinedButton(text = "Download Track", onClick = downloadTrack)
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
