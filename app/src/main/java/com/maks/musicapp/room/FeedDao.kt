package com.maks.musicapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.maks.musicapp.data.domain.Feed

@Dao
interface FeedDao {
    @Query("SELECT * FROM feed WHERE type = :type")
    suspend fun getFeeds(type:String): List<Feed>

    @Insert
    suspend fun insertFeeds(feeds: List<Feed>)
}