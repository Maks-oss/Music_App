package com.maks.musicapp.repository

import android.content.Context
import com.maks.musicapp.utils.datastore
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
   suspend fun setAccessToken(accessToken:String)
   fun retrieveAccessToken(): Flow<String?>

}