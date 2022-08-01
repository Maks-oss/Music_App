package com.maks.musicapp.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.app.NotificationCompat
import com.maks.musicapp.R
import com.maks.musicapp.data.domain.Track
import com.maks.musicapp.utils.MediaPlayerUtil
import com.maks.musicapp.utils.RemoteViewsProcessor
import com.maks.musicapp.utils.getTrackTitle
import kotlinx.coroutines.*
import java.util.*


@SuppressLint("Currently not supported")
class MusicForegroundService : Service() {
    private var isPlaying = false
    private var job: Job? = null
    private lateinit var remoteViewsProcessor: RemoteViewsProcessor

    @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
    override fun onBind(intent: Intent?): IBinder? = null

    @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startServiceCommand(intent)
        return START_STICKY_COMPATIBILITY
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        remoteViewsProcessor =
            RemoteViewsProcessor(this@MusicForegroundService, MediaPlayerUtil.mediaPlayer)
    }

    @OptIn(
        ExperimentalMaterialApi::class,
        ExperimentalFoundationApi::class
    )

    private fun startServiceCommand(intent: Intent?) {


        job = CoroutineScope(Dispatchers.IO).launch {
            val mediaPlayer = MediaPlayerUtil.mediaPlayer
            isPlaying = intent!!.getBooleanExtra("isPlaying", true)
            val track = intent.getSerializableExtra("track") as Track
            remoteViewsProcessor.apply {
                    playButton(track, isPlaying)

                setTrackTitle(track.getTrackTitle())
                setTrackImage(track.image!!)

                setPlayButtonImage(isPlaying)
                setTrackMinutes()
                setTrackProgress()
            }
            Log.d("TAG", "startServiceCommand: $isPlaying")
            if (!isPlaying) {
                mediaPlayer.pause()
            } else {
                if (!mediaPlayer.isPlaying) {
                    mediaPlayer.start()
                }
            }

            val notification =
                NotificationCompat.Builder(this@MusicForegroundService, "1001")
                    .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                    .setSmallIcon(R.drawable.music_logo)
                    .setCustomContentView(remoteViewsProcessor.musicNotificationCollapsed)
                    .setCustomBigContentView(remoteViewsProcessor.musicNotificationExpanded)
//                    .build()
            if (isPlaying) {
                launch {
                    while (mediaPlayer.currentPosition != mediaPlayer.duration) {
                        remoteViewsProcessor.apply {
                            setTrackMinutes()
                            setTrackProgress()
                        }
                        notification
                            .setCustomContentView(remoteViewsProcessor.musicNotificationCollapsed)
                            .setCustomBigContentView(remoteViewsProcessor.musicNotificationExpanded)

                        startForeground(1, notification.build())
                    }
                }
            } else {
                startForeground(1, notification.build())
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()

        job?.cancel()
        stopSelf()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                "1001",
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                enableLights(false)
                setShowBadge(false)
                enableVibration(false)
                setSound(null, null)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }
}
