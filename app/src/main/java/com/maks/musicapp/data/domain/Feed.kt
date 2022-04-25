package com.maks.musicapp.data.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Feed(
    @PrimaryKey
    val id: String,
    val title: String,
    val text: String,
    val type: String,
)