package com.maks.musicapp.services

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.app.NotificationCompat
import com.maks.musicapp.MainActivity
import com.maks.musicapp.R
import com.maks.musicapp.data.domain.Track
import com.maks.musicapp.utils.RemoteViewsProcessor
import com.maks.musicapp.utils.getTrackTitle


class MusicForegroundService : Service() {
    @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
    override fun onBind(intent: Intent?): IBinder? = null

    @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startServiceCommand(intent)
        return START_STICKY
    }
    @OptIn(ExperimentalMaterialApi::class,
        ExperimentalFoundationApi::class
    )
    private fun startServiceCommand(intent: Intent?) {
        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }
        createNotificationChannel()
        val track = intent?.getSerializableExtra("track") as Track
        val remoteViewsProcessor = RemoteViewsProcessor(this).apply {
            setTrackTitle(track.getTrackTitle())
            setTrackImage(track.image!!)
        }
        val notificationBuilder = NotificationCompat.Builder(this, "1001")
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setSmallIcon(R.drawable.music_logo)
            .setCustomContentView(remoteViewsProcessor.musicNotificationCollapsed)
            .setCustomBigContentView(remoteViewsProcessor.musicNotificationExpanded)
        startForeground(1, notificationBuilder.build())
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