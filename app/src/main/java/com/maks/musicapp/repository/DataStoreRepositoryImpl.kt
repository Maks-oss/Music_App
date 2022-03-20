package com.maks.musicapp.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.maks.musicapp.utils.PreferencesKeys
import com.maks.musicapp.utils.datastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreRepositoryImpl(context: Context) : DataStoreRepository {
    private val dataStore = context.datastore
    override suspend fun setAccessToken(accessToken: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.ACCESS_TOKEN] = accessToken
        }
    }

    override fun retrieveAccessToken(): Flow<String?> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.ACCESS_TOKEN]
    }
}