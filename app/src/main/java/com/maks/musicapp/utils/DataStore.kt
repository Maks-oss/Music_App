package com.maks.musicapp.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "appAccessToken")

object PreferencesKeys {
    val ACCESS_TOKEN = stringPreferencesKey("accessToken")
}