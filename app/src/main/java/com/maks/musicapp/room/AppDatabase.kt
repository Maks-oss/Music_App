package com.maks.musicapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maks.musicapp.data.domain.Feed

@Database(entities = [Feed::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun feedDao(): FeedDao
}