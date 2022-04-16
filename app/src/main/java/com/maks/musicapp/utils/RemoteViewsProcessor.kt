package com.maks.musicapp.utils

import android.content.Context
import android.net.Uri
import android.widget.RemoteViews
import com.maks.musicapp.R

class RemoteViewsProcessor(private val context: Context) {
    val musicNotificationExpanded =
        RemoteViews(context.packageName, R.layout.music_notification_layout_expanded)
    val musicNotificationCollapsed =
        RemoteViews(context.packageName, R.layout.music_notification_layout_collapsed)

    fun setTrackTitle(title: String) {
        musicNotificationExpanded.setTextViewText(R.id.notification_artist_name_expanded, title)
        musicNotificationCollapsed.setTextViewText(R.id.notification_artist_name_collapsed, title)
    }
    fun setTrackImage(image:String){

        musicNotificationExpanded.setImageViewUri(R.id.notification_track_image_expanded, Uri.parse(image))
        musicNotificationCollapsed.setImageViewUri(R.id.notification_track_image_collapsed, Uri.parse(image))
    }
}