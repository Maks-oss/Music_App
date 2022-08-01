package com.maks.musicapp.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.widget.RemoteViews
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.maks.musicapp.R
import com.maks.musicapp.data.domain.Track
import com.maks.musicapp.services.MusicForegroundService

class RemoteViewsProcessor(private val context: Context, private val mediaPlayer: MediaPlayer) {
    val musicNotificationExpanded =
        RemoteViews(context.packageName, R.layout.music_notification_layout_expanded)
    val musicNotificationCollapsed =
        RemoteViews(context.packageName, R.layout.music_notification_layout_collapsed)
    private val PLAY_BUTTON = 1
    fun setTrackTitle(title: String) {
        musicNotificationExpanded.setTextViewText(R.id.notification_artist_name_expanded, title)
        musicNotificationCollapsed.setTextViewText(R.id.notification_artist_name_collapsed, title)
    }

    fun playButton(track: Track, isPlaying: Boolean) {
        val pendingIntent = PendingIntent.getService(
            context, PLAY_BUTTON,
            Intent(context, MusicForegroundService::class.java).apply {
                putExtra("track", track)
                putExtra("isPlaying", !isPlaying)
                action = "pass track ${track.id}"

            }, PendingIntent.FLAG_CANCEL_CURRENT
        )
        musicNotificationExpanded.setOnClickPendingIntent(
            R.id.notification_play_button,
            pendingIntent
        )
    }

    fun setPlayButtonImage(isPlaying: Boolean) {
        val imageRes =
            if (isPlaying) android.R.drawable.ic_media_pause else android.R.drawable.ic_media_play

        musicNotificationExpanded.setImageViewResource(R.id.notification_play_button, imageRes)
    }

    fun setTrackProgress() {
        musicNotificationExpanded.setProgressBar(
            R.id.notification_track_slider,
            mediaPlayer.duration,
            mediaPlayer.currentPosition,
            false
        )
    }

    fun setTrackMinutes() {
        musicNotificationExpanded.setTextViewText(
            R.id.notification_track_start,
            mediaPlayer.currentPosition.toMinutes()
        )
        musicNotificationExpanded.setTextViewText(
            R.id.notification_track_end,
            mediaPlayer.duration.toMinutes()
        )
    }

    fun setTrackImage(image: String) {
        Glide.with(context)
            .asBitmap()
            .load(image)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    musicNotificationCollapsed.setImageViewBitmap(
                        R.id.notification_track_image_collapsed,
                        resource
                    )
                    musicNotificationExpanded.setImageViewBitmap(
                        R.id.notification_track_image_expanded,
                        resource
                    )
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })

    }

}
