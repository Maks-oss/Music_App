package com.maks.musicapp.utils

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.maks.musicapp.data.domain.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

fun Number.toMinutes(): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this.toLong())
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this.toLong()) -
            TimeUnit.MINUTES.toSeconds(minutes)
    return "${if (minutes < 10) "0$minutes" else minutes}:${if (seconds < 10) "0$seconds" else seconds}"

}

fun SnackbarHostState.showMessage(
    message: String,
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    coroutineScope.launch {
        showSnackbar(message, "OK")
    }
}

fun Context.downloadTrack(
    track: Track
) {
    val downloadManagerRequest = DownloadManager.Request(Uri.parse(track.audio)).apply {
        setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            track.name.plus(".mp4")
        )
        setTitle(track.name)
        setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    }
    val manager: DownloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    manager.enqueue(downloadManagerRequest)
}

fun Track.getTrackTitle(): String {
    val trackNameLines = "$artist_name - $name".split(" ")
    return StringBuilder().apply {
        trackNameLines.forEachIndexed { index, string ->
            if (index % AppConstants.WORDS_PER_LINES == 0 && index != 0) {
                append("\n$string")
            } else {
                append("$string ")
            }
        }
    }.toString()
}

@SuppressLint("ComposableModifierFactory")
@Composable
fun Modifier.setBackground(selectedItem: Int, expectedItem: Int) =
    background(
        if (selectedItem == expectedItem) MaterialTheme.colors.primarySurface else MaterialTheme.colors.surface,
        shape = RoundedCornerShape(8.dp)
    )
