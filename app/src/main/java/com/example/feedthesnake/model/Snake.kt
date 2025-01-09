package com.example.feedthesnake.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "snakes")
data class Snake(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val score: Int
)
