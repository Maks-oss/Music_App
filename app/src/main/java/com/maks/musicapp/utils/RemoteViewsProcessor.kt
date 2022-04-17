package com.maks.musicapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.RemoteViews
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
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

