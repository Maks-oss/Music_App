package com.maks.musicapp.ui.broadcastreceivers

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.maks.musicapp.R
import com.maks.musicapp.utils.AppConstants

class TrackDownloadBroadCast(private val showMessage: (String) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        showMessage(context.getString(R.string.track_download_success))
    }

}